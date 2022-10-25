#include <stdio.h>
#include <stdlib.h>


//c语言获取随机数  0-99随机数
int getRandNumber(){
    return rand() % 100;    
}

 
main(){
       
   printf("%d\n", getRandNumber()); // 41
   printf("%d\n", getRandNumber()); // 67
   system("pause");       
}
