# self-service-kiosk
EBU6304 Software Engineering(2019/20) Group Project

Screenshot: 

![Screenshot 1](https://github.com/ForeverCyril/self-service-kiosk/raw/master/readme_res/v1/screenshot1.png)

![Screenshot 2](https://github.com/ForeverCyril/self-service-kiosk/raw/master/readme_res/v1/screenshot2.png)



## How to build

#### Build

```shell
./gradlew build
```

#### Run

```shell
./gradlew run
```

#### Get JavaDoc

```shell
./gradlew javadoc # javadoc will generated in build/docs/
```

#### Test

```shell
./gradlew test # result will generated to build/test-results
```



#### Notice

- If you are using windows, please use `.\gradlew.bat` instead of ` ./gradlew`

- If you get error message like  `Permission denied`, please confirm you have the permission of execution for `gradlew` .(Below is the example of how to give permission in Linux)

  - ```shell
    chmod +x ./gradlew
    ```

