# 文件上传

## 一、环境
jdk: java8_202
springboot: 2.7.11
docker: 
os: windows
maven: 3.8.3

## 二、文件上传介绍
以下文件上传方式针对springboot
1. form-data 文件上传
    ```java
    public FileResponseVO formDataFileUpload(@RequestParam(value = "file", required = true) MultipartFile multipartFile) throws IOException, ApplicationException {
        String tempFileName = FileUtil.generateTempFileName();
        String fileSaveTempPath = FILE_TEMP_PATH + File.separator + tempFileName;
        File file = new File(fileSaveTempPath);
        multipartFile.transferTo(file.getAbsoluteFile());
        ...
    }
    ```
2. binary 二进制上传
    ```java
    public FileResponseVO binaryFileUpload(HttpServletRequest servletRequest) throws ApplicationException {
        InputStream inputStream = servletRequest.getInputStream();
        ...
    }
    ```
3. raw 文本文件上传
    ```java
     public FileResponseVO binaryFileUpload(HttpServletRequest servletRequest) throws ApplicationException {
        InputStream inputStream = servletRequest.getInputStream();
        ...
    }
    ```
## 三、本地调试
1. 使用 idea 启动
2. 使用命令行启动
   ```shell
   mvn spring-boot:run
   ```
3. 参数设置
   ```properties
   # 设置文件存放的临时位置和最终位置及大小
   file.destination.temp.path=D:\\Projects\\JavaProjects\\fileupload\\temp
   file.destination.save.path=D:\\Projects\\JavaProjects\\fileupload\\save
   file.destination.file.maxsize=10485760

   # multipart & form-data 上传的文件使用以下两个参数设置
   spring.servlet.multipart.max-file-size=10MB
   spring.servlet.multipart.max-request-size=100MB
   ```
# 四、打包部署
采用docker命令打包以及部署，本地需安装 docker，请自行安装
dockerfile
```shell
FROM java:8
# 创建目录
RUN mkdir -p /fileupload
RUN mkdir -p /fileupload/save
RUN mkdir -p /fileupload/temp
# 拷贝文件
COPY ./target/fileupload-0.0.1-SNAPSHOT.jar /fileupload/fileupload-0.0.1-SNAPSHOT.jar
# 暴露端口
EXPOSE 8090
# 启动命令
CMD ["java", "-jar","-Dfile.destination.temp.path=/fileupload/temp","-Dfile.destination.save.path=/fileupload/save", "/fileupload/fileupload-0.0.1-SNAPSHOT.jar"]
```
1. 打包命令，制作镜像
   ```shell
   pwd # path/fileupload/
   mvn clean package
   docker build -t fileupload .   
   ```
2. 启动容器
   ```shell
   docker container run -dp 8090:8010 -t fileupload
   ```
## 五、nmap 扫描
1. 安装 nmap：https://nmap.org/download.html#windows

2. 获取容器id
```shell
docker container ls 
# (CONTAINER ID   IMAGE               COMMAND                  CREATED         STATUS         PORTS      NAMES)
# (ea4921395ce6   fileupload:latest   "java -jar -Dfile.de…"   4 minutes ago   Up 4 minutes   8090/tcp   elated_ritchie)
```
3. 获取容器 ip
```shell
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
```
4. 扫描
```shell
nmap -p 1-65535 container_ip_address
```

## 六、nmap 扫描补充
1. 拉取 nmap 镜像
```shell
docker pull instrumentisto/nmap
```

2. 启动扫描
```shell
docker run --rm -it instrumentisto/nmap -A -T4 ip
```
