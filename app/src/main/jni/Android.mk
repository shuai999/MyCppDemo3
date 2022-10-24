LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_LDLIBS += -llog

#要生成的 so库文件名称
LOCAL_MODULE:=hellolib
#这个是我们写的 hello.c文件 ，然后编译后叫做 hellolib动态库，在java中加载这个库，然后调用定义的 native方法
LOCAL_SRC_FILES:=hello.c
include $(BUILD_SHARED_LIBRARY)