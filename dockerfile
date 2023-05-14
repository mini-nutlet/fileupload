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