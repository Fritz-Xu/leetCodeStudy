leetCode 刷题用



#### 分组循环的模板：

```kotlin
 n = size - 1
 i = 0
 while(i < n){
    val start = i
    while(start < n && (其他判断条件)){
        i++
    }
    //从 start 到 i-1 是一组
    //下一组从 i 开始，无需 i += 1
 }
    
```



#### 二分的闭区间通用模板:

```kotlin
var start = 0 
var end = size - 1
while (start < end) {
    // 这里 + 1 是为了避免死循环
    long mid = (start + end + 1) / 2
    if (check(mid)) {
        start = mid;
    } else {
        end = mid - 1;
    }
}
```
