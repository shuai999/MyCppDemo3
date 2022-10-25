#include <jni.h> // 把jni.h头文件 导入进来后， 就可以用 jni.h 中所有的变量、方法
#include <string.h> // strlen()获取 c的字符串长度 , 在 这个头文件里边
#include <stdlib.h>
#include <stdio.h>

// c语言 打印日志， 需要添加如下， 同时给
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/**
* 在这里 实现我们需要的功能：
    1. 拷贝 jni.h 到 jni目录
    2. 把在 Dev c++中写好的功能拷贝到这里
    3. 把 jni规范 从.h文件拷贝过来。 写功能，这个jni方法 ， 就是  java调用c
*/

// 这里必须把 C的代码拷贝过来， 开发中，这里就是C开发工程师写好，自己光写下边的jni规范
char* getHello(){

     // 这里有可能会有很多代码，都是C/C++ 开发者写好，把方法名 getHello暴露给java，直接调用方法，
     //然后就可以获取该方法里边的功能
     return "hellor world from c";
}

// a + b ————》  复制 C语言写的方法
int add(int a, int b){
    return a+b;
}

// 把 DEV c++的 mul方法复制过来
int mul(int a, int b){
    return a*b;
}

// 对数组中的每个值  +1
void addArray(int* datas, int length){
     int i=0;
     for(; i<length; i++){
         datas[i]++;
     }
}

// 插入排序算法
void insertSort(int* datas, int length){
     // 插入排序的外层循环，插入排序的次数
     int i=0;
        for (i=1; i<length; i++){
            int temp = datas[i];
            int j=0;
            for (j=i-1; j>0; j--){
                if (datas[j] > temp) {
                    // 把j的位置 放到后边，就是 j+1
                    datas[j+1] = datas[j];
                } else {
                    datas[j+1] = temp;
                    break;//跳出循环
                }
            }
            if (j == -1) {
                datas[0] = temp;
            }
        }
}


// 校验用户名和密码字符串 登录， c中的字符串用 char* name表示
int checkLogin(char* name, char* pwd){
    // 表示2个字符串相等
    if(strcmp(name, "andy") == 0){
        // 上边的用户名成功，这里就判断密码是否一样
        if(strcmp(pwd, "123")==0){
             return 200;
        }else {
              return 500;
        }
    } else{
        // 2个字符串不一样
       return 404;
    }
}


// 要加密的 字符串
 void jiami(char *str,int len){
      int i = 0;
      for (; i < len;i++){
          str[i]++; // 比如是a，++后会变成 b
      }
 }



// public native String helloFromC();
// 在c中 要定义， java调用这个方法的 jni协议规范  jni是java和c调用的桥梁
// 方法的返回类型  Java_包名_类名_方法名(JNIEnv *env, jobject obj)  固定写法，每个jni方法都需要这2个参数
// 2个参数是 jni中间件实例化的，不是自己实例化
jstring Java_com_czy_mycppdemo3_TestActivity_helloFromC(JNIEnv *env, jobject obj){

    // jstring     (*NewStringUTF)(JNIEnv*, const char*);
    // 返回类型jstring  函数指针(*NewStringUTF)(JNIEnv*, const char*);
    // JNIEnv* ： 中间件， 能把 java转为c,  也可以把c转为java

// 这里用右边 2种方式调用： (*env)->getVersion 、  (**env).getVersion
    jstring str = (*env)->NewStringUTF(env, getHello());
    return str;

    // 这个是 windows 平台下的， 要用 ndk工具，把这个编译成 Linux 平台下的 能够运行的二进制 文件。就是动态库文件
}


// JNIEnv *env, jobject obj 固定参数；   后边的 jint a, jint b 2个参数
jint Java_com.czy.mycppdemo3_AddActivity_add(JNIEnv *env, jobject obj, jint a, jint b){
    return add(a, b);
}

// c++对应 java的  jni规范
jint Java_com_czy_mycppdemo3_MulActivity_mul(JNIEnv *env, jobject obj, int a, int b){
    return mul(a*b);
}

// 在Terminal的 E:\project\MyCppDemo3\app\src\main\java目录
// 用 javah 包名.类名  生成的 头文件，复制过来 JNIEXPORT 和 JNICALL没用，可以删除
// jintArray：是c语言中的数组，这里需要先把 java的int数组 转为 c的数组
JNIEXPORT void JNICALL Java_com_czy_mycppdemo3_SortActivity_addDatas
  (JNIEnv *env, jobject obj, jintArray jArray, jint length){
  // 返回 jint类型数组，指针类型： jint* (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
  //   jboolean* 是c语言中的 unsigned char， 给0就行 ， 0表示 不拷贝数组， 直接把数组拿出来就行
 // 这里 返回 jint类型 数组
      jint* jintArray = (*env)->GetIntArrayElements(env, jArray, 0);
      addArray(jintArray, length);
 }


// 插入排序 jni规范
 JNIEXPORT void JNICALL Java_com_czy_mycppdemo3_SortActivity_insertSort
   (JNIEnv *env, jobject obj, jintArray jArray, jint length){
   // 这里原则就是： 把参数的类型 转为 j开头的类型， j开头对应的就是 java的类型
       jint* jintArray = (*env)->GetIntArrayElements(env, jArray, 0);
       insertSort(jintArray, length);
  }


// java字符串 转为 c 字符串
char* string_java2c(JNIEnv *env,jstring name){
	 //1.
	 jclass clazz =  (*env)->FindClass(env,"java/lang/String");
	 //2. jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
	 jmethodID mid = (*env)->GetMethodID(env,clazz,"getBytes","(Ljava/lang/String;)[B");
	 //3.jobject     (*CallObjectMethod)(JNIEnv*, jobject, jmethodID, ...);
	 jbyteArray jba = (*env)->CallObjectMethod(env,name,mid,(*env)->NewStringUTF(env,"utf-8"));
	 //4.jbyte*      (*GetByteArrayElements)(JNIEnv*, jbyteArray, jboolean*);
	 jbyte* jb = (*env)->GetByteArrayElements(env,jba,JNI_FALSE);
	 //5.jsize       (*GetArrayLength)(JNIEnv*, jarray);
	 jint len = (*env)->GetArrayLength(env,jba);
	 char m[10];

	 if (len == 0){
		 return "";
	 }

	 // 自己动态申请的内存空间。  一般创建字符串，就要这样 malloc动态申请内存空间。
	 char *str = malloc(len + 1);
	 memcpy(str,jb,len);
	 str[len] = 0;
	// 5.void        (*ReleaseByteArrayElements)(JNIEnv*, jbyteArray,jbyte*, jint)
	 (*env)->ReleaseByteArrayElements(env,jba,jb,0);
	 return str;
 }



// 校验用户名、密码是否正确  JNIEXPORT、JNICALL 可以删除，有没有都可以
//JNIEXPORT jint JNICALL Java_com_czy_mycppdemo3_IcbcActivity_checkLogin
  //(JNIEnv *env, jobject jobj, jstring name, jstring pwd){
    // jni中最难的就是： 把 java的字符串  ——  转为 c的字符串
    //char* cname = string_java2c(env, name);
    //char* cpwd = string_java2c(env, pwd);

    //return checkLogin(cname, cpwd);
 //}

 // c调用java的 closeMessage方法
 void c_Close(JNIEnv *env, jobject jobj){
    // 1. 获取Class类型对象
    // jclass (*FindClass)(JNIEnv*, const char*); 参数1：env； 参数2：类名
    jclass clazz = (*env)->FindClass(env, "com/czy/mycppdemo3/IcbcActivity2");

     // 2. 获取方法名   ()V  --->
     // 括号里边的是 参数，无参就是() .  括号外边的 V是返回类型， 是void， 构造方法没有返回类型
     // jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
     jmethodID method = (*env)->GetMethodID(env, clazz, "closeMessage", "()V");

     // 3. 创建对象obj

     // 4. 调用  方法.对象（正常是：对象.方法）
     // void  (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
     (*env)->CallVoidMethod(env, obj, method);
 }


 // c 调用 java的 setName方法，把name传递给 setName，回显到 java中
 void c_setname(JNIEnv *env, jobject jobj, char* name){
     // 1. 获取Class类型对象
     jclass clazz = (*env)->FindClass("com/czy/mycppdemo3/IcbcActivity2");

     // 2. 获取method  方法名
     // jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
     jmethodID method = (*env)->GetMethodID(env, clazz, "setName", "(Ljava/lang/String;)V");

     // 3. 创建对象obj,  就是 IcbcActivity2， 这里就是 obj对象

     // 4. 调用  方法.对象（正常是：对象.方法）
     // void  (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);

     // 把 c字符串 转为 java的字符串jstring
     // jstring (*NewStringUTF)(JNIEnv*, const char*);
     jstring str = (*env)->NewStringUTF(env, name);

     (*env)->CallVoidMethod(env, obj, method, str);
 }

 // c调用java的 showMessage方法
 void c_Show(JNIEnv *env, jobject jobj, char* msg){
     // 1. 获取Class类型对象    c把字符串 传递到 java中， 用反射。
     // jclass (*FindClass)(JNIEnv*, const char*); 参数1：env； 参数2：类名
     jclass clazz = (*env)->FindClass(env, "com/czy/mycppdemo3/IcbcActivity2");

     // 2. 获取方法名
    // jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);

    // 参数3：java中的方法名，这里是showMessage；
    // 参数4：showMessage方法的签名  在cmd中用  javap -s java.lang.String，查找方法签名
    //   public void showMessage(String msg){} (Ljava/lang/String;)V
    //                         L开头, 分号结尾, 中间是参数类型，void返回值是V
    jmethodID method = (*env)->GetMethodID(env, clazz, "showMessage", "(Ljava/lang/String;)V");

     // 3. 创建对象obj,  就是 IcbcActivity2， 就是这里的 obj参数。

     // 4. 调用  方法.对象（正常是：对象.方法）
     // void  (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
     // 参数2：obj对象，就是 IcbcActivity2；参数3：方法签名method；
     // 参数4：参数，是可变参数，方法输入的参数， 这里只有一个字符串 String

     // 把 c字符串 转为 java的字符串jstring
     // jstring (*NewStringUTF)(JNIEnv*, const char*);
     jstring str = (*env)->NewStringUTF(env, msg);

     (*env)->CallVoidMethod(env, obj, method, str);
 }

  // ---------- 这里 先是 java 调用 C
  // Java_com_czy_mycppdemo3_IcbcActivity_checkLogin--------

 // 改版 ————  java写好弹窗Dialog， c来调用
 // 校验用户名、密码是否正确  JNIEXPORT、JNICALL 可以删除，有没有都可以
 JNIEXPORT jint JNICALL Java_com_czy_mycppdemo3_IcbcActivity_checkLogin
   (JNIEnv *env, jobject jobj, jstring name, jstring pwd){
     // jni中最难的就是： 把 java的字符串  ——  转为 c的字符串
     char* cname = string_java2c(env, name);
     char* cpwd = string_java2c(env, pwd);

     // 上边定义的方法
     int result = checkLogin(cname, cpwd);

     // 根据 result，设置字符串，就是 用户名正在加密处理、密码正在加密处理、登录成功等字符串。
     // 把这个 msg传递给java的 showMessage方法，showMessage自己处理这个字符串
     char* msg = "用户名正在加密处理";

     // --------- 然后这里 C 回调 java c_Show -------------------
     c_Show(env, obj, msg);

    // 加密
     jiami(cname, strlen(cname));

     // java中写 setName方法， c 调用 java的 setName方法。 然后在 该方法回显 加密后的name
     // 此处的 cname 是 加密后的名字
     c_setname(env, obj, cname);

    // 休息2s， c原因以秒为单位。
    sleep(2);
     // dialog显示后，休息2s，然后关闭dialog
     c_Close(env, obj);

     // checkLogin上边写的方法，在c里边校验 登录结果， 把int结果返回给java
     return checkLogin(cname, cpwd);
  }



 // java给c传递一个 字符串，c给前边加一个 hello，然后返回给我
  char* getHelloName(char* name, int length){
        // char[] str ; 这种方式申请内存获取不到值，所以一般都用 malloc申请内存空间。
        // 动态申请空间  ——————
        // 只要是 malloc动态申请内存， 一定要乘以 对应类型的 个数 sizeof(char)、sizeof(int)等。
        char* res = malloc((6 + length + 1) * sizeof(char)); // 6是hello:   1是\0

        // 申请好空间后， c工程师 把hello拼接到 传递过来的 字符串前边
        // 字符串： 先拷贝再拼接。
        // 要给前边 拼接的 字符串
        char* helloStr = "hello:";
        // 先把 hello 拷贝给 res 这块内存
        strcpy(res, helloStr);
        // 字符串拼接： 把hello 拼接到 res前边
        strcat(res, name);
        return res;
  }

/**
* try {
*     // 这个是 java中的正常调用，目的是这个。
*     // 现在需要在 hello.c 中 反射调用， 是 用字符串调用它的 getBytes方法， 就是 "andy".getBytes("utf-8")
*     byte[] b = "andy".getBytes("utf-8");
*     } catch (UnsupportedEncodingException e) {
*        e.printStackTrace();
*     }
*/
// 把 java 字符串 转为 c的字符串
char* java2CString(JNIEnv *env, jobject obj, jstring str){
    LOGI("java2CString 已经开始执行了.......");

        // java中的 byte[] 对应于 c中的 char* [] 字符数组
     // 1. 获取 jclass  ——  这里是 字符串类String
     jclass clazz = (*env)->FindClass(env, "com/czy/mycppdemo3/IcbcActivity2");

     // 2. 获取 methodId  这里方法名就是 getBytes， 固定写法   [表示返回值是数组，B表示返回是bytes
     jmethodID method = (*env)->GetMethodID(env, clazz, "getBytes", "(Ljava/lang/String;)[B");

    // 3. 获取字符串， 就是 str

    // 4. 调用  方法.对象（正常是：对象.方法） 下边的 jobject就是 指针
    // jobject  (*CallObjectMethod)(JNIEnv*, jobject, jmethodID, ...);
    jstring jstr = (*env)->NewStringUTF(env, "utf-8");
    // 把字符串 先 转为 字节数组
    jbyteArray jba  = (*env)->CallObjectMethod(env, str, method, jstr);

    // 5. 获取到数组array
    // jbyte* 就是 unsigned char 无符号
    // jbyte* (*GetByteArrayElements)(JNIEnv*, jbyteArray, jboolean*);
    jbyte* jb = (*env)->GetByteArrayElements(env, jba, 0);

    // 6. 获取数组长度 array length
    // jsize  (*GetArrayLength)(JNIEnv*, jarray);
    jsize length = (*env)->GetArrayLength(env, jba);

    LOGI("长度length：%d\n", length);

    // 7. 动态申请一块内存
    // 字符数组
    char* cstr = malloc(length + 1);
    // 把 字节指针jb的内容 ， 拷贝到 字符数组cstr中
    // 内存拷贝  参数1：目标的； 参数2：原来的； 参数3：拷贝的字串长度
    memcpy(cstr, jb, length);

    // 8. 给字符串 加 \0 结束符
    cstr[len] = '\0';

    LOGI("cstr：%s\n", cstr);

    // 9. 清理jbyteArray内存   参数4：从0的位置开始
    // void (*ReleaseByteArrayElements)(JNIEnv*, jbyteArray, jbyte*, jint);
    (*env)->ReleaseByteArrayElements(env, jba, jb, 0);
    return cstr;
}

// JNIEXPORT、JNICALL 可以删除
// 1和2 都是 c写好， java直接在 这个方法里边 写下边的2步 ， 把结果返回就行了
jstring Java_com_czy_mycppdemo3_Java2CActivity_getHelloFromC
  (JNIEnv *env, jobject obj, jstring str){
    // 1. 把 java 字符串 转为 c的字符串
    char* name = java2CString(env, obj, str);
    LOGI("name：%s\n", name);

    // 2. java给c传递一个 字符串，c给前边加一个 hello，然后返回给我
    char* result = getHelloName(name, strlen(len));
     LOGI("result：%s\n", result);

    jstring jstr = (*env)->NewStringUTF(env, result);
    return jstr;
}

// java 调用c， 获取压力值  JNIEXPORT和JNICALL可以删除
JNIEXPORT jint JNICALL Java_com_czy_mycppdemo3_guolu_GuoluActivity_getPressValue
  (JNIEnv *env, jobject jobj) {

        // 把 DEV c++ 写的 功能 cstr.c文件拷贝到 jni目录，这里直接调用 cstr.c文件中方法就行
        return getRandNumber();
  }


int state;  //0是false；  其余都是true

// 开始监控 ：  c语言开始监控  ： 需求：每隔一秒，给我返回一次压力值，死循环
JNIEXPORT void JNICALL Java_com_czy_mycppdemo3_guolu_GuoluActivity2_startMonitor
  (JNIEnv *env, jobject jobj) {
        state = 1; // 开始监控
        while(state) {
            // 1. c中 获取到锅炉的压力值
            int press = getRandNumber();

            // 2. c调用 java， 调用 setMyProgress显示 当前压力值
            // A: 获取 Class
            jclass clazz = (*env)->FindClass(env, "com/czy/mycppdemo3/guolu/GuoluActivity2");
            // B: 获取方法 method  参数3：方法名； 参数4：参数是int，返回值是void
            jmethodID methodId = (*env)->GetMethodID(env, clazz, "setMyProgress", "(I)V");
            // C：创建obj，上边参数的 obj
            // D. 调用  方法.对象（正常是：对象.方法）
            // jobject  (*CallObjectMethod)(JNIEnv*, jobject, jmethodID, ...);
            (*env)->CallVoidMethod(env, obj, methodId, press);

            // 每隔1秒返回一个压力值
            sleep(1);
        }
  }

// 停止监控  让上边的 死循环停止
JNIEXPORT void JNICALL Java_com_czy_mycppdemo3_guolu_GuoluActivity2_stopMonitor
  (JNIEnv *env, jobject jobj){
        state = 0;  // 就停止监控
  }

    // ------------- 第一版
  // 杀不死方法 —— 让软件卸载不了
  //JNIEXPORT void JNICALL Java_com_czy_mycppdemo3_xiezai_XieZaiActivity_shabusi
    //(JNIEnv *env, jobject jobj) {

      //  // 分叉函数  0表示成功
       // int result = fork();
        //if(result == 0) {
            // 分叉成功
          //  while(1) {
          //  LOGI("===== 你卸载不掉。。。。=====");
                // 每隔一秒钟，循环一次
            //    sleep(1);
            //}
        //}
   // }


    // -- 第二版 -- 用户卸载apk后，让跳转一个 qq网页，然后让用户输入为啥卸载原因，然后提交
    JNIEXPORT void JNICALL Java_com_itheima_removelistener_MainActivity_shabusi
      (JNIEnv *env, jobject obj)
    {
    	//fork();  获取进程 拆分进程
    		int flag = 1;
    		pid_t pid = fork(); //拆分进程  pid_t：还是int类型
    		if (pid == 0) { //0拆分成功
    			while(flag) {
    				//监视文件夹是否存在。
    				//FILE *fopen(char *filename, char *type); 打开文件， rw: 打开文件的模式
    				FILE* f = fopen("/data/data/com.czy.mycppdemo3.xiezai","rw");
    				if(f==NULL) {
    					//文件不存在
    					LOGI("not exist,bei xiele");
    					//调用c代码执行一个外部命令，开启界面
    					// am: ActivityManager： activity管理者
    					// 卸载完apk后， 用 am strart -a android.intent.action.VIEW -d http://www.qq.com，
    					// 用 上边的网址 跳转到 qq页面，让用户填写卸载的原因。
    					// 下边的网址，一般就是写自己服务器的地址，跳转到自己服务器地址
    					execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
    							"-d", "http://www.qq.com", NULL);
    					// 停止打印，停止循环
    					flag = 0;
    				} else {
    					//文件存在
    					LOGI("exist");
    				}
    				sleep(1);
    			}
    		}
    }