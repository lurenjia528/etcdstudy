# study
## etcd
etcd etcd.v2api etcd.v3api java ssl

按照教程搭建带tls认证的etcd集群后，会生成三个文件
`etcd-ca.pem`  `etcd-key.pem`  `etcd.pem`
与下面命令对应  

``` bash
 etcdctl --cacert /etc/etcd/ssl/etcd-ca.pem --cert /etc/etcd/ssl/etcd.pem --key /etc/etcd/ssl/etcd-key.pem get /hello
```
显示所有头key
```bash
 etcdctl --cacert /etc/etcd/ssl/etcd-ca.pem --cert /etc/etcd/ssl/etcd.pem --key /etc/etcd/ssl/etcd-key.pem get / --prefix --keys-only
```

v3版本api认证需要PKCS8类型的key.pem文件，由以下命令生成

` openssl pkcs8 -topk8 -nocrypt -in etcd-key.pem -out pkcs8-key.pem`

etcd restapi 

`http://editor.swagger.io/`

`https://github.com/etcd-io/etcd/blob/master/Documentation/dev-guide/apispec/swagger/rpc.swagger.json`

`https://github.com/etcd-io/etcd/blob/master/Documentation/dev-guide/api_grpc_gateway.md`

arm架构下连接etcd，需要netty-tcnative-2.0.12.Final-linux-aarch_64.jar，添加进依赖

restapi
例如：v3

`curl -X POST -H 'Content-type: application/json' -d '{}' --cacert /etc/etcd/ssl/etcd-ca.pem --cert /etc/etcd/ssl/etcd.pem --key /etc/etcd/ssl/etcd-key.pem https://127.0.0.1:2379/v3alpha/cluster/member/list`

`{"header":{"cluster_id":"9387297561454088963","member_id":"8976880612339134383","raft_term":"115"},"members":[{"ID":"8679419496537157493","name":"kube-etcd2","peerURLs":["http://192.168.17.161:2380"],"clientURLs":["https://192.168.17.161:2379"]},{"ID":"8976880612339134383","name":"kube-etcd1","peerURLs":["http://192.168.17.160:2380"],"clientURLs":["https://192.168.17.160:2379"]},{"ID":"12506278219037213787","name":"kube-etcd3","peerURLs":["http://192.168.17.162:2380"],"clientURLs":["https://192.168.17.162:2379"]}]}`

`curl -X POST -H 'Content-type: application/json' -d '{}' --cacert /etc/etcd/ssl/etcd-ca.pem --cert /etc/etcd/ssl/etcd.pem --key /etc/etcd/ssl/etcd-key.pem https://127.0.0.1:2379/metrics`



## harbor

安装harbor后

harbor token使用
https://github.com/goharbor/harbor/wiki/Harbor-FAQs#api

harbor api 

`https://github.com/goharbor/harbor/blob/master/docs/swagger.yaml` 

复制到

`http://editor.swagger.io/`

访问时：

`curl -u "admin:Harbor12345" http......`

相当于 `curl -H "Authorization: Basic YWRtaW46SGFyYm9yMTIzNDU="`(admin:Harbor12345的base64编码，注意Basic中间的空格)

转换到java 添加请求头 

`key=authorization` `value= Basic YWRtaW46SGFyYm9yMTIzNDU=`

## json

jsoniter fastjson

## jar

增加读取jar包中文件测试

## k8s

k8s Restful Api 和客户端的使用

需要把k8s集群master节点上的~/.kube/config 复制到此电脑的相同目录(~/.kube/config)

## freemarker 

freemarker 调用java方法

## 添加发送邮件测试

## 添加actuator监控测试

访问路径：http://localhost:8888/actuator/metrics  health  info  ...


## 添加文件上传/下载测试

http://localhost:8888/file/upload

http://localhost:8888/file/download

## 添加简单websocket

## docker remote api

开启普通http接口
```bash
dockerd -H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock

cat docker.socket
[Unit]
Description=Docker Socket for the API
PartOf=docker.service

[Socket]
ListenStream=/var/run/docker.sock
SocketMode=0660
SocketUser=root
SocketGroup=docker

[Install]
WantedBy=sockets.target

```

开启tls认证的remote 接口

参考 https://docs.docker.com/engine/security/https/

服务端
```bash
ls /root/.docker/tls
ca.pem  server-cert.pem  server-key.pem
```

客户端
```bash
ls /home/ht061/.docker/tls
ca.pem  cert.pem  key.pem
```

```bash
dockerd -H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock --tlsverify --tlscacert=/root/.docker/tls/ca.pem --tlscert=/root/.docker/tls/server-cert.pem --tlskey=/root/.docker/tls/server-key.pem
```

添加依赖
//对低版本不太友好(https remote api)
com.github.docker-java:docker-java:3.1.2
//对低版本也支持
com.spotify:docker-client:8.15.2