mis_1:
	*1.departmentAction 在未登录时的处理未实现到最优-->已经解决未登录的拦截，但未解决登录超时的拦截--> 对三种情况的登录都已经解决了
	无法使用记住密码功能-->本地使用localhost网址时，就会出项此问题
	
	*2.org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint.commence()
		获取AccessDeniedException跳转到登录页面
	org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy.onInvalidSessionDetected()
		登录超时跳转
	org.springframework.security.web.session.ConcurrentSessionFilter.doFilter()
		登录被占跳转
	org.springframework.security.web.access.ExceptionTranslationFilter.sendStartAuthentication();
		记录因为登录被打断的请求
	org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler.onAuthenticationSuccess()
		登录成功后的跳转，重新恢复因为登录被打断的请求（在sendStartAuthentication中没记录就不会跳转）
	org.springframework.security.web.access.AccessDeniedHandlerImpl.handle()
		无权限页面
	org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler.onAuthenticationFailure
		登录失败跳转
	org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler.onAuthenticationSuccess
		登录成功跳转
		
	*3.保存数据时，不能包含 <'>
		更新权限名称(已解决)
	*4.读取数据成json时，不能包含  " { } [ ]
		部门列表 权限列表
		解决方案：读取后或保存前将\ ' " 三种符号去掉
		
mis_2:
	*1.主界面使用easyUI框架，但是出现了不少麻烦，比如变量，比如javascript脚本等等载入
	-2.role_addInput.jsp无法动态载入jquery.ztree.excheck-3.4.js
	*3.index.action在登录超时的时候的特殊处理(已处理)
	-4.在easyUI的tabs中打开关闭页面后原页面的id等等不会消失，这可能带来一些未知麻烦
	
mis_3:
	-1.个人信息修改界面未添加(未处理)
	-2.增加系统恢复默认功能，自动创建id为1的用户和id为1的角色
	-3.可以考虑增加允许删除用户的功能，在删除用户时，将所有与他有关的新闻、回复等等，全部转移到id1的用户身上
	*4.action与“_rd.action”结尾，继续支持spring security 的页面跳转方式

mis_4:
	*1.主页载入运行记录表会报 $.data(jq[0], "datagrid") is undefined和$.data(_76c, "combobox") is undefined(已处理)
	-2.直接在页面上输入news_list_rd.action?page.title=标题	进行搜索，会出现中文乱码情况(未处理)
		在百度页面上也会出现同样的问题
		这是url的percentcode百分号编码问题，浏览器直接输入url默认将其解析成GB2312编码
	-3.设备列表上上传文件后不会正常提示(未处理)
	*4.在java代码中使用.replaceAll("'", "").replaceAll("\"", "").replaceAll("\\\\", "")
		在javascript代码中使用.replace(/\\/g, '').replace(/"/g, '').replace(/'/g, '')
		将\ ' " 三种符号去掉，避免读取json格式数据和查询sql语句出错
		 
mis_5:
	-1.在新闻列表news_list_rd.action页面的作者一行加上部门一信息
	*2.新闻点击最多保存为：2147483647(2的11次方)
	-3.在新闻评论上添加删除权限的判断，在页面载入前，先做一次过滤，让删除按钮只让新闻发表人自己和评论人自己以及拥有权限的人看见(未处理)
	*4._js.action --> ajax方式访问	无实际应用地方
	  _rd.action --> redirect方式跳转访问	在spring security各个验证权限的方法上添加例外
	  _rf.action --> ajax refresh 方式只是触发的访问	在index.js打开tab上 
	  _no.action --> 不需要security判断权限，手动判断	在  SecurityMetadataSourceImpl.getAttributes过滤配置上，NewsreplyManagerImpl.remove上使用
	  _no_rd.action --> 综合no和rd
	*5.没有权限： {\"status\":1,\"mess\":\"没有权限！\"}
	       未登录：    {\"status\":1,\"mess\":\"请先登录！\",\"login\":false}
	       密码错误：{\"status\":1,\"mess\":\"账号或密码错误！\"}
	       处理：
		$.get("href", function(data) {
			if(data.status == 1) {
				index_mess(data.mess);
				if(data.login == false) {
					index_login();
				}
			} else {
				index_mess("成功！");
			}
		});
	-6.更改多数据的id类型为long
	-7.keditor多文件上传，使用的是SWFUpload插件，除ie浏览器外，其他浏览器将新建一个ie浏览器的session，(未处理)
		如果ie浏览器中选择了，记住密码这功能，那么第一张图片将去服务器做登录认证，其他的将因认证成功，
		将上传到ie浏览器中的那个用户上面去，所以firefox浏览器要屏蔽掉这功能，防止误传到他人文件夹。
	-8.文件上传之前添加判断大小的功能，目前可以用(未处理)
		IE:
			var fso = new ActiveXObject("Scripting.FileSystemObject");   
   			alert("文件大小为："+fso.GetFile(文件路径).size);
   			或者：(只有图片有效)
   			var image = new Image();
			function getSize() {
				if(image.readyState!="complete") {
					setTimeout("getSize()",500);
					return false;
				}
				alert(image.fileSize);
			}
			function getFileSize(file) {
				image.src = file.value;
				getSize();
			}
			<input type="file" onchange="getFileSize(this)">
		HTML5:
			document.getElementById('file').files[0].size(name, type)
		但还没有可以一起使用的代码
	*9.get/getJSON方法传中文全改为post
	*10.在Tomcat server.xml文件添加<Connector port="8080" URIEncoding="utf-8"/>
	-11.文件管理还差
		1)文件夹大小，即容量的控制(未处理)
		2)移动文件功能，在更改名称的功能上修改即可(未处理)
		3)在linux上使用(未处理)
			FileManagerImpl 93 行	Collections.sort(fileList, new NameComparator());
			当名字有乱码的时候，就会出错
mis_6:
	*1.防止ie惰性查询<?_=" + Math.random()>
	*2.使用com.baodian.util.JSONValue.escapeHTML();可以处理特殊字符'"<>/r...
	*3.编代码时将index.js中的cache:true关闭，
	      运行将其打开，并在url后加上更改日期.js?_20121224，防止读取了缓存而忽略了更新
	*4.关于SecurityMetadataSourceImpl 1.authorityMap 2.menuList 3.defaultMenu的改进，页面修改后直接修改内存(未处理)
		权限 增：2 3	删：1 2 3*	改：1 2
		角色：增删改：1* 2*
		带*表示不能用单条语句直接改，要遍历内容后才能定位
	*5.为登录用户添加ip属性：
		1)com.baodian.security.IPTokenBasedRememberMeServices的getUserIPAddress可以获取ip
		2)org.springframework.security.core.session.SessionRegistryImpl中保存有每个登录用户的id，但未发现ip保存在哪里
		3)org.springframework.security.web.authentication.WebAuthenticationDetails有ip出现，但未知怎么取到他
		4)已发现保存当前用户ip的位置，但还未找到所有的位置，见：SecuManagerImpl.currentIp
		5)最后修改org.springframework.security.web.authentication.session.
			ConcurrentSessionControlStrategy， 把ip也保存进去
mis_7:
	*1.关于json文件流text/x-json的说明
		1)如果文字包含&#34;等一些特殊字符，使用text/html输出时，这样的实体形式就会被替换成最终的字符(双引号")，在json格式接收时就会干扰；
			如果用jquery直接接受就会发现这问题，但如果用easyUI的panel等接受，它会进行处理，此问题是不会出现的
		2)如果设置为text/x-json就可以消除，但是一些浏览器会提示下载内容
		3)最好的办法是设置为text/plain无格式正文输出，那么就可以解决1和2的问题
		4)但是使用iframe上传数据时，JSON格式会自动补充<pre>标记，造成错误
			因此在这种方式提交数据时，指定为text/html，例：kindeditor上传文件
		5)jquery和easyUI中的panel等获取数据后要将数据转换为json格式：
			a: 就简单的就是使用getJSON或指定返回类型dataType:"JSON"
			b: 转换data = eval('(' + data + ')');
			c: 推荐使用这种data = $.parseJSON(data);
		**最终方案统一采用text/plain，上传文件中使用text/html
	*2.关于StringBuilder替换String
		StringBuilder json = new StringBuilder();
		添加:json.append('[');
		改部分：json.replace(0, 5, "where");
		清除：json.setLength(0);
		只去掉最后一个：json.deleteCharAt(json.length()-1);
		去掉最后一个，并加上：
			1)json.setCharAt(json.length()-1, ']');
			*)return json.substring(0, json.length()-1) + ']';
		去掉最后一个，并加二个：
			*)json.replace(json.length()-1, json.length(), "]},");
			2)json.deleteCharAt(json.length()-1).append("]},");
			3)json.setCharAt(json.length()-1, ']').append('}');
			**)return sql.substring(0, sql.length()-1) + "]}";
		插入：json.insert(0, "{");
		
		B)返回时，去掉最后一个','再加上']'
		if(json.length() != 1)
			return json.substring(0, json.length()-1) + ']';
		return json + ']';
	*3.关于时间的同步问题，tomcat和mysql的时间可能不一致
		1)此外datetime支持的范围为'1000-01-01 00:00:00'到'9999-12-31 23:59:59'
			而TIMESTAMP值不能早于1970或晚于2037
		2)但是datetime使用8个字节存储，而TIMESTAMP是4个
		*)在数据插入timestamp时间戳，使用now()传入的都更改为server端自己生成的时间
mis_8:
	-1.邮件和首页服务器传出的时间由字符串格式改为long格式，这样生成及处理起来都比较容易(未处理)
	*2.邮件收取未读邮件标准代码：
	  ja发送：
	  	return "{\"status\":0,\"today\":\"" + ft.format(new Date()) +
				"\",\"urnums\":" + user_EmailDao.getUnReadByUId(uid) + "}";
	  js接收：如果能用一个函数处理就最佳
		clearInterval(timeon);
		index_mess("", 0);
		$.getJSON(url, function(data) {
			timeon = setInterval(autorf, rftm);
			if(data.status == 1) {
				index_mess(data.mess, 1);
				if(data.login == false) {
					emst.login = false;
				}
				return;
			}
			emst.login = true;
			Today[4] = data.today;
			emst.urnums = data.urnums;
			chkunread();
			index_mess("", 2);
		});
		现在用另一个函数处理：handleUnread({type, url, params, mess, method});
	-3.发件箱查看时，每封邮件都要查找三个用户，速度太慢(未处理)
	-4.关于将mysql中text格式转变为varchar类型
		1)这两个的最大长度都是65535
	-5.email中转发和邮件查看下一封的功能未实现
	-6.email和news等的内容要放在iframe中，防止出现css/div注入
	*7.javascript跳转：
		1)新窗口打开:window.open(url);
		2)当前页面跳转：window.location.href = url;
		2)刷新：window.location.reload();
mis_9:
	*1.返回空容器
		return Collections.emptyList();
	*2.考虑为权限增加为角色赋权的功能，以及角色为用户赋权的功能
mis_20130422
	1.为设备管理增加密码加密查看功能
	2.个人目录未实现
	3.设备统计功能需要和故障记录联系起来
	4.重新设计设备管理
	
file_20130711
	1.上传文件可以判断其大小，但是要等文件上传完之后才能判断
	*2.需要增加uploadify实现多文件上传
	3.更新图片，增加美观
	*4.实现移动文件的功能
	*5.给更改名称和创建文件夹增加按下enter键的功能
	6.增加日志保存查看功能，服务器和数据库各一份
	*7.上传之前先获取一个sessionid
	*8.flash上传会有登录超时的提示，增加file_session_js.action权限

file_20130801
	1.增加多文件打包下载功能
	2.将session持久化到数据库，使重启和多服务器工作时保持

file_20140307
	*1.ie下载时会调用迅雷，造成下载失败
	2.ie上空格会变成加号
	3.ie11会更改不了名字
	4.ie11使用js无法重复登录

	