
default:	build

clean:
	rm -rf Makefile objs

build:
	$(MAKE) -f objs/Makefile

install:
	$(MAKE) -f objs/Makefile install

upgrade:
	/usr/local/sbin/nginx -t

	kill -USR2 `cat /usr/local/logs/nginx.pid`
	sleep 1
	test -f /usr/local/logs/nginx.pid.oldbin

	kill -QUIT `cat /usr/local/logs/nginx.pid.oldbin`

upgrade1:
	# upgrade 0.1.x to 0.2+

	/usr/local/sbin/nginx -t

	cp /usr/local/logs/nginx.pid /usr/local/logs/nginx.pid.oldbin

	kill -USR2 `cat /usr/local/logs/nginx.pid`
	sleep 1
	test -f /usr/local/logs/nginx.pid.oldbin

	cp /usr/local/logs/nginx.pid /usr/local/logs/nginx.pid.newbin

	kill -QUIT `cat /usr/local/logs/nginx.pid.oldbin`
