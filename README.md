# stackoverflowlogin
- stackoverflow自动登录小程序
## 使用说明：
1. 添加<code>user.properties</code>中用户名（邮箱）、密码。
2. 添加<code>SendMail.java</code>发件邮箱授权码。
## 下载地址：[stackoverflowlogin](https://github.com/Ai-yoo/stackoverflowlogin/archive/master.zip)

---

## 实现功能
- **自动登录stackoverflow**  
## 项目简要说明
- 使用HttpClient，附带表单参数，向stackoverflow发送post请求  
## 开发说明流程
1. 使用浏览器分析stackoverflow的登录页面需要提交的表单数据  
2. 使用浏览器检查分析登录提交的方式  
3. 通过查看登录页面源代码，得到表单数据提交的地址  
4. 读取properties配置文件中的用户名和密码
5. 手动创建表单数据  
6. 使用HttpClient附带表单数据向网站发送post请求，提交登录  

---

## 项目最终实现  
- 打成jar包，发布在服务器上，编写定时脚本，每天定时执行  
### 服务器执行脚本<code>login.sh</code>  
- 脚本实现功能：执行程序，并输出执行时间到日志文件log.log中
```
java -cp stackoverflowlogin-1.0-SNAPSHOT.jar com.duzhentong.Login
echo "$(date +%Y-%m-%d_%H:%M:%S) runned!" >> /usr/local/stack/log.log
```
### 程序定时执行脚本
- 输入<code>crontab -e</code>回车执行输入以下内容：
```
00 06 * * * /usr/local/stack/login.sh
```
- 脚本实现功能：每天早上六点执行`login.sh`

---

## 程序运行出错处理：
**通过程序执行返回的状态码判断是否执行成功**
- 200：执行成功
- 302：执行成功，网址被重定向  

判断除了上面的状态码，其余代表程序执行出错，并通过JavaMail向指定邮箱发送错误报告，详见：[SendMail.java](https://github.com/Ai-yoo/stackoverflowlogin/blob/master/src/main/java/com/util/SendMail.java)
### 邮件内容：

<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
 </head>
 <body bgcolor="gray">
  大哥！！！出错了！！！登不上了！！！<br><br><font style="font-weight:bold;" size="9">别玩了！！！</font>
 </body>
</html>
