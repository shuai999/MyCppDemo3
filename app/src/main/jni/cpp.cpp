#include <stdio.h>  // c++的 头文件
// c++这里，一定要把生成的 头文件 拷贝到jni目录，然后在.cpp中 用#include引用过来
#include <com_czy_mycppdemo3_cpp_CppActivity.h>

// c++ 方式， java 调用 c++， 从 c++中获取 helloworld
JNIEXPORT jstring JNICALL Java_com_czy_mycppdemo3_cpp_CppActivity_helloFromCpp
  (JNIEnv *env, jobject jobj) {
       jstring str = (env)->NewStringUTF("hello from cpp");
       return str;
  }