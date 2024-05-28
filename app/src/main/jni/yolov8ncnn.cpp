// Tencent is pleased to support the open source community by making ncnn available.
//
// Copyright (C) 2021 THL A29 Limited, a Tencent company. All rights reserved.
//
// Licensed under the BSD 3-Clause License (the "License"); you may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
// https://opensource.org/licenses/BSD-3-Clause
//
// Unless required by applicable law or agreed to in writing, software distributed
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// CONDITIONS OF ANY KIND, either express or implied. See the License for the
// specific language governing permissions and limitations under the License.

#include <android/asset_manager_jni.h>
#include <android/native_window_jni.h>
#include <android/native_window.h>
#include <set>

#include <iostream>

#include <android/log.h>

#include <jni.h>

#include <string>
#include <vector>

#include <platform.h>
#include <benchmark.h>
#include <chrono>
#include <thread>

#include "yolo.h"

#include "ndkcamera.h"

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#if __ARM_NEON
#include <arm_neon.h>
#endif // __ARM_NEON

long long  temp = 0;
JNIEnv *tempenv = nullptr;
jobject tempobj;
float zoomratio;
static int draw_unsupported(cv::Mat& rgb)
{
    const char text[] = "unsupported";

    int baseLine = 0;
    cv::Size label_size = cv::getTextSize(text, cv::FONT_HERSHEY_SIMPLEX, 1.0, 1, &baseLine);

    int y = (rgb.rows - label_size.height) / 2;
    int x = (rgb.cols - label_size.width) / 2;

    cv::rectangle(rgb, cv::Rect(cv::Point(x, y), cv::Size(label_size.width, label_size.height + baseLine)),
                    cv::Scalar(255, 255, 255), -1);

    cv::putText(rgb, text, cv::Point(x, y + label_size.height),
                cv::FONT_HERSHEY_SIMPLEX, 1.0, cv::Scalar(0, 0, 0));

    return 0;
}
bool isFirstTime = true;
static int draw_fps(cv::Mat& rgb)
{
    // resolve moving average
    float avg_fps = 0.f;
    {
        static double t0 = 0.f;
        static float fps_history[10] = {0.f};

        double t1 = ncnn::get_current_time();
        if (t0 == 0.f)
        {
            t0 = t1;
            return 0;
        }

        float fps = 1000.f / (t1 - t0);
        t0 = t1;

        for (int i = 9; i >= 1; i--)
        {
            fps_history[i] = fps_history[i - 1];
        }
        fps_history[0] = fps;

        if (fps_history[9] == 0.f)
        {
            return 0;
        }

        for (int i = 0; i < 10; i++)
        {
            avg_fps += fps_history[i];
        }
        avg_fps /= 10.f;
    }

    char text[32];
    sprintf(text, "FPS=%.2f", avg_fps);

    int baseLine = 0;
    cv::Size label_size = cv::getTextSize(text, cv::FONT_HERSHEY_SIMPLEX, 0.5, 1, &baseLine);

    int y = 0;
    int x = rgb.cols - label_size.width;

    cv::rectangle(rgb, cv::Rect(cv::Point(x, y), cv::Size(label_size.width, label_size.height + baseLine)),
                    cv::Scalar(255, 255, 255), -1);

    cv::putText(rgb, text, cv::Point(x, y + label_size.height),
                cv::FONT_HERSHEY_SIMPLEX, 0.5, cv::Scalar(0, 0, 0));

    return 0;
}

static Yolo* g_yolo = 0;
static ncnn::Mutex lock;

class MyNdkCamera : public NdkCameraWindow
{
public:
    virtual void on_image_render(cv::Mat& rgb) const;
};
std::vector<Object> objectlist;
static MyNdkCamera* g_camera = 0;
void MyNdkCamera::on_image_render(cv::Mat& rgb) const
{
    auto now = std::chrono::system_clock::now(); // Get the current time
    auto since_epoch = now.time_since_epoch(); // Get the duration since epoch

    // Convert to milliseconds
    auto millis = std::chrono::duration_cast<std::chrono::milliseconds>(since_epoch);
    long long currentMillis = millis.count();
    std::cout << currentMillis << temp << "\n";
    double avgBrightness = cv::mean(rgb)[0];  // Calculate average brightness
    const double BRIGHTNESS_THRESHOLD = 50.0;
    bool isOnflash = avgBrightness < BRIGHTNESS_THRESHOLD;

    // nanodet
    {
        ncnn::MutexLockGuard g(lock);

        if (g_yolo ) {
            temp = currentMillis;
            std::vector<Object> objects;
            g_yolo->detect(rgb, objects);
            objectlist = objects;
            g_yolo->draw(rgb, objects);
        }
    }

    draw_fps(rgb);
    if(isOnflash && isFirstTime){
        __android_log_print(ANDROID_LOG_DEBUG, "checking flash", "Hello flash");
        g_camera->open(1, true,zoomratio);
        isFirstTime = false;
    }
//    std::this_thread::sleep_for(std::chrono::milliseconds(30));
}
static const char* class_names[] = {
        "Accordion", "Adhesive tape", "Aircraft", "Airplane", "Alarm clock", "Alpaca", "Ambulance", "Animal", "Ant", "Antelope",
        "Apple", "Armadillo", "Artichoke", "Auto part", "Axe", "Backpack", "Bagel", "Baked goods", "Balance beam", "Ball",
        "Balloon", "Banana", "Band-aid", "Banjo", "Barge", "Barrel", "Baseball bat", "Baseball glove", "Bat (Animal)", "Bathroom accessory",
        "Bathroom cabinet", "Bathtub", "Beaker", "Bear", "Bed", "Bee", "Beehive", "Beer", "Beetle", "Bell pepper",
        "Belt", "Bench", "Bicycle", "Bicycle helmet", "Bicycle wheel", "Bidet", "Billboard", "Billiard table", "Binoculars", "Bird",
        "Blender", "Blue jay", "Boat", "Bomb", "Book", "Bookcase", "Boot", "Bottle", "Bottle opener", "Bow and arrow",
        "Bowl", "Bowling equipment", "Box", "Boy", "Brassiere", "Bread", "Briefcase", "Broccoli", "Bronze sculpture", "Brown bear",
        "Building", "Bull", "Burrito", "Bus", "Bust", "Butterfly", "Cabbage", "Cabinetry", "Cake", "Cake stand",
        "Calculator", "Camel", "Camera", "Can opener", "Canary", "Candle", "Candy", "Cannon", "Canoe", "Cantaloupe",
        "Car", "Carnivore", "Carrot", "Cart", "Cassette deck", "Castle", "Cat", "Cat furniture", "Caterpillar", "Cattle",
        "Ceiling fan", "Cello", "Centipede", "Chainsaw", "Chair", "Cheese", "Cheetah", "Chest of drawers", "Chicken", "Chime",
        "Chisel", "Chopsticks", "Christmas tree", "Clock", "Closet", "Clothing", "Coat", "Cocktail", "Cocktail shaker", "Coconut",
        "Coffee", "Coffee cup", "Coffee table", "Coffeemaker", "Coin", "Common fig", "Common sunflower", "Computer keyboard", "Computer monitor", "Computer mouse",
        "Container", "Convenience store", "Cookie", "Cooking spray", "Corded phone", "Cosmetics", "Couch", "Countertop", "Cowboy hat", "Crab",
        "Cream", "Cricket ball", "Crocodile", "Croissant", "Crown", "Crutch", "Cucumber", "Cupboard", "Curtain", "Cutting board",
        "Dagger", "Dairy Product", "Deer", "Desk", "Dessert", "Diaper", "Dice", "Digital clock", "Dinosaur", "Dishwasher",
        "Dog", "Dog bed", "Doll", "Dolphin", "Door", "Door handle", "Doughnut", "Dragonfly", "Drawer", "Dress",
        "Drill (Tool)", "Drink", "Drinking straw", "Drum", "Duck", "Dumbbell", "Eagle", "Earrings", "Egg (Food)", "Elephant",
        "Envelope", "Eraser", "Face powder", "Facial tissue holder", "Falcon", "Fashion accessory", "Fast food", "Fax", "Fedora",
        "Filing cabinet", "Fire hydrant", "Fireplace", "Fish", "Flag", "Flashlight", "Flower", "Flowerpot", "Flute", "Flying disc",
        "Food", "Food processor", "Football", "Football helmet", "Footwear", "Fork", "Fountain", "Fox", "French fries", "French horn",
        "Frog", "Fruit", "Frying pan", "Furniture", "Garden Asparagus", "Gas stove", "Giraffe", "Girl", "Glasses", "Glove",
        "Goat", "Goggles", "Goldfish", "Golf ball", "Golf cart", "Gondola", "Goose", "Grape", "Grapefruit", "Grinder",
        "Guacamole", "Guitar", "Hair dryer", "Hair spray", "Hamburger", "Hammer", "Hamster", "Hand dryer", "Handbag", "Handgun",
        "Harbor seal", "Harmonica", "Harp", "Harpsichord", "Hat", "Headphones", "Heater", "Hedgehog", "Helicopter", "Helmet",
        "High heels", "Hiking equipment", "Hippopotamus", "Home appliance", "Honeycomb", "Horizontal bar", "Horse", "Hot dog", "House", "Houseplant",
        "Human arm", "Human beard", "Human body", "Human ear", "Human eye", "Human face", "Human foot", "Human hair", "Human hand", "Human head",
        "Human leg", "Human mouth", "Human nose", "Humidifier", "Ice cream", "Indoor rower", "Infant bed", "Insect", "Invertebrate", "Ipod",
        "Isopod", "Jacket", "Jacuzzi", "Jaguar (Animal)", "Jeans", "Jellyfish", "Jet ski", "Jug", "Juice", "Kangaroo",
        "Kettle", "Kitchen & dining room table", "Kitchen appliance", "Kitchen knife", "Kitchen utensil", "Kitchenware", "Kite", "Knife", "Koala", "Ladder",
        "Ladle", "Ladybug", "Lamp", "Land vehicle", "Lantern", "Laptop", "Lavender (Plant)", "Lemon", "Leopard", "Light bulb",
        "Light switch", "Lighthouse", "Lily", "Limousine", "Lion", "Lipstick", "Lizard", "Lobster", "Loveseat", "Luggage and bags",
        "Lynx", "Magpie", "Mammal", "Man", "Mango", "Maple", "Maracas", "Marine invertebrates", "Marine mammal", "Measuring cup",
        "Mechanical fan", "Medical equipment", "Microphone", "Microwave oven", "Milk", "Miniskirt", "Mirror", "Missile", "Mixer", "Mixing bowl",
        "Mobile phone", "Monkey", "Moths and butterflies", "Motorcycle", "Mouse", "Muffin", "Mug", "Mule", "Mushroom", "Musical instrument",
        "Musical keyboard", "Nail (Construction)", "Necklace", "Nightstand", "Oboe", "Office building", "Office supplies", "Orange", "Organ (Musical Instrument)", "Ostrich",
        "Otter", "Oven", "Owl", "Oyster", "Paddle", "Palm tree", "Pancake", "Panda", "Paper cutter", "Paper towel",
        "Parachute", "Parking meter", "Parrot", "Pasta", "Pastry", "Peach", "Pear", "Pen", "Pencil case", "Pencil sharpener",
        "Penguin", "Perfume", "Person", "Personal care", "Personal flotation device", "Piano", "Picnic basket", "Picture frame", "Pig", "Pillow",
        "Pineapple", "Pitcher (Container)", "Pizza", "Pizza cutter", "Plant", "Plastic bag", "Plate", "Platter", "Plumbing fixture", "Polar bear",
        "Pomegranate", "Popcorn", "Porch", "Porcupine", "Poster", "Potato", "Power plugs and sockets", "Pressure cooker", "Pretzel", "Printer",
        "Pumpkin", "Punching bag", "Rabbit", "Raccoon", "Racket", "Radish", "Ratchet (Device)", "Raven", "Rays and skates", "Red panda",
        "Refrigerator", "Remote control", "Reptile", "Rhinoceros", "Rifle", "Ring binder", "Rocket", "Roller skates", "Rose", "Rugby ball",
        "Ruler", "Salad", "Salt and pepper shakers", "Sandal", "Sandwich", "Saucer", "Saxophone", "Scale", "Scarf", "Scissors",
        "Scoreboard", "Scorpion", "Screwdriver", "Sculpture", "Sea lion", "Sea turtle", "Seafood", "Seahorse", "Seat belt", "Segway",
        "Serving tray", "Sewing machine", "Shark", "Sheep", "Shelf", "Shellfish", "Shirt", "Shorts", "Shotgun", "Shower",
        "Shrimp", "Sink", "Skateboard", "Ski", "Skirt", "Skull", "Skunk", "Skyscraper", "Slow cooker", "Snack",
        "Snail", "Snake", "Snowboard", "Snowman", "Snowmobile", "Snowplow", "Soap dispenser", "Sock", "Sofa bed", "Sombrero",
        "Sparrow", "Spatula", "Spice rack", "Spider", "Spoon", "Sports equipment", "Sports uniform", "Squash (Plant)", "Squid", "Squirrel",
        "Stairs", "Stapler", "Starfish", "Stationary bicycle", "Stethoscope", "Stool", "Stop sign", "Strawberry", "Street light", "Stretcher",
        "Studio couch", "Submarine", "Submarine sandwich", "Suit", "Suitcase", "Sun hat", "Sunglasses", "Surfboard", "Sushi", "Swan",
        "Swim cap", "Swimming pool", "Swimwear", "Sword", "Syringe", "Table", "Table tennis racket", "Tablet computer", "Tableware", "Taco",
        "Tank", "Tap", "Tart", "Taxi", "Tea", "Teapot", "Teddy bear", "Telephone", "Television", "Tennis ball",
        "Tennis racket", "Tent", "Tiara", "Tick", "Tie", "Tiger", "Tin can", "Tire", "Toaster", "Toilet",
        "Toilet paper", "Tomato", "Tool", "Toothbrush", "Torch", "Tortoise", "Towel", "Tower", "Toy", "Traffic light",
        "Traffic sign", "Train", "Training bench", "Treadmill", "Tree", "Tree house", "Tripod", "Trombone", "Trousers",
        "Truck", "Trumpet", "Turkey", "Turtle", "Umbrella", "Unicycle", "Van", "Vase", "Vegetable", "Vehicle",
        "Vehicle registration plate", "Violin", "Volleyball (Ball)", "Waffle", "Waffle iron", "Wall clock", "Wardrobe", "Washing machine", "Waste container",
        "Watch", "Watercraft", "Watermelon", "Weapon", "Whale", "Wheel", "Wheelchair", "Whisk", "Whiteboard", "Willow",
        "Window", "Window blind", "Wine", "Wine glass", "Wine rack", "Winter melon", "Wok", "Woman", "Wood-burning stove",
        "Woodpecker", "Worm", "Wrench", "Zebra", "Zucchini"
};

 JavaVM * g_vm = nullptr;

extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    __android_log_print(ANDROID_LOG_DEBUG, "ncnn", "JNI_OnLoad");

    g_camera = new MyNdkCamera;
    g_vm = vm;
//    ::vm = vm;

    return JNI_VERSION_1_4;
}

JNIEXPORT void JNI_OnUnload(JavaVM* vm, void* reserved)
{
    __android_log_print(ANDROID_LOG_DEBUG, "ncnn", "JNI_OnUnload");

    {
        ncnn::MutexLockGuard g(lock);

        delete g_yolo;
        g_yolo = 0;
    }

    delete g_camera;
    g_camera = 0;
}

// public native boolean loadModel(AssetManager mgr, int modelid, int cpugpu);
JNIEXPORT jboolean JNICALL Java_com_example_thirdeye_Yolov8Ncnn_loadModel(JNIEnv* env, jobject thiz, jobject assetManager, jint modelid, jint cpugpu)
{
    if (modelid < 0 || modelid > 6 || cpugpu < 0 || cpugpu > 1)
    {
        return JNI_FALSE;
    }

    AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);

    __android_log_print(ANDROID_LOG_DEBUG, "ncnn", "loadModel %p", mgr);

    const char* modeltypes[] =
    {
        "n",
        "s",
    };

    const int target_sizes[] =
    {
        320,
        320,
    };

    const float mean_vals[][3] =
    {
        {103.53f, 116.28f, 123.675f},
        {103.53f, 116.28f, 123.675f},
    };

    const float norm_vals[][3] =
    {
        { 1 / 255.f, 1 / 255.f, 1 / 255.f },
        { 1 / 255.f, 1 / 255.f, 1 / 255.f },
    };

    const char* modeltype = modeltypes[(int)modelid];
    int target_size = target_sizes[(int)modelid];
    bool use_gpu = (int)cpugpu == 1;

    // reload
    {
        ncnn::MutexLockGuard g(lock);

        if (use_gpu && ncnn::get_gpu_count() == 0)
        {
            // no gpu
            delete g_yolo;
            g_yolo = 0;
        }
        else
        {
            if (!g_yolo)
                g_yolo = new Yolo;
            g_yolo->load(mgr, modeltype, target_size, mean_vals[(int)modelid], norm_vals[(int)modelid], use_gpu);
        }
    }

    return JNI_TRUE;
}
jobject thiz;
// public native boolean openCamera(int facing);
JNIEXPORT jboolean JNICALL Java_com_example_thirdeye_Yolov8Ncnn_openCamera(JNIEnv* env, jobject thiz, jint facing,jfloat magnifyinglevel)
{
    if (facing < 0 || facing > 1)
        return JNI_FALSE;

    __android_log_print(ANDROID_LOG_DEBUG, "ncnn camera", "openCamera %d %f", facing,magnifyinglevel);
    zoomratio = magnifyinglevel;
    g_camera->open((int)facing,false,magnifyinglevel);
    isFirstTime = true;

    return JNI_TRUE;
}

// public native boolean closeCamera();
JNIEXPORT jboolean JNICALL Java_com_example_thirdeye_Yolov8Ncnn_closeCamera(JNIEnv* env, jobject thiz)
{
    __android_log_print(ANDROID_LOG_DEBUG, "ncnn", "closeCamera");
    g_camera->close();
    return JNI_TRUE;
}

// public native boolean setOutputWindow(Surface surface);
JNIEXPORT jboolean JNICALL Java_com_example_thirdeye_Yolov8Ncnn_setOutputWindow(JNIEnv* env, jobject thiz, jobject surface)
{

    ANativeWindow* win = ANativeWindow_fromSurface(env, surface);

    __android_log_print(ANDROID_LOG_DEBUG, "ncnn", "setOutputWindow %p", win);

    g_camera->set_window(win);

    return JNI_TRUE;
}

}



extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_example_thirdeye_Yolov8Ncnn_getObjectlist(JNIEnv *env, jobject thiz) {// TODO: implement getObjectlist()
    jclass stringClass = env->FindClass("java/lang/String");
    std::set<std::string> st;

    // Create a jobjectArray with size equal to the number of elements in objectNames
    jobjectArray stringArray = env->NewObjectArray(objectlist.size(), stringClass, nullptr);
    for (size_t i = 0; i < objectlist.size(); i++) {
        const Object &obj = objectlist[i];
        char text[256];
        sprintf(text, "%s %.1f%%", class_names[obj.label], obj.prob * 100);
        jstring stringElement = env->NewStringUTF(class_names[obj.label]);
        if(st.find(class_names[obj.label])!=st.end()){
            continue;
        }else{
            st.insert(class_names[obj.label]);
        }

        // Set the element in the array
        env->SetObjectArrayElement(stringArray, i, stringElement);

        // Manage the local reference by deleting it
        env->DeleteLocalRef(stringElement);
    }
    return stringArray;

}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_thirdeye_Yolov8Ncnn_ChangeZoomRatio(JNIEnv *env, jclass clazz, jfloat ratio) {
    // TODO: implement ChangeZoomRatio()
//    g_camera->setZoom(ratio);
    zoomratio = ratio;
    g_camera->open(1,false,ratio);
}