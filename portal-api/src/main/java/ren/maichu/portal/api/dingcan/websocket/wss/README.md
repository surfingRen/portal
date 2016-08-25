wss 协议在不通过服务端代码实现

方案采用 nginx 反向代理的形式

http://stackoverflow.com/questions/10381412/nginx-config-for-wss


	I have at least solved it for the short term by using stunnel (referring to this article: http://www.darkcoding.net/software/proxy-socket-io-and-nginx-on-the-same-port-over-ssl/).
	
	Stunnel can convert HTTPS to HTTP and by that token WSS to WS. Nginx served the socket application running on 9000 port as usual:

/etc/stunnel/stunnel.conf

	[https]
	accept  = 443
	connect = 80 
	TIMEOUTclose = 0

/usr/local/nginx/conf/nginx.conf

	#user  nobody;
	worker_processes  1;  
	error_log  logs/error.log;
	#error_log  logs/error.log  notice;
	#error_log  logs/error.log  info;
	#pid        logs/nginx.pid;
	events {
	    worker_connections  1024;
	}
	tcp {
     upstream websockets {
      ## Play! WS location
       server 127.0.0.1:9000;
       check interval=3000 rise=2 fall=5 timeout=1000;
     }    
    server {
        listen 80; 
        listen 8000;
        server_name socket.artoo.in;
        tcp_nodelay on; 
        proxy_pass websockets;
        proxy_send_timeout 300;
    }   
     # virtual hosting
     #include /usr/local/nginx/vhosts/*;
	}

没配成功。。，尝试不使用反向代理

http://external.pureload.com/doc/extensions/server/websocket/ssl.html

