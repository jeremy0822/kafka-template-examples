var _doc=document.getElementsByTagName('head')[0];
var script=document.createElement('script');
script.setAttribute('type','text/javascript');
script.setAttribute('src','http://ca.cubead.com/cubead_ca.js?seq=' + Math.floor(Math.random() * ( 99999999 + 1)));
_doc.appendChild(script);
script.onload=script.onreadystatechange=function(){
   if(!this.readyState||this.readyState=='loaded'||this.readyState=='complete'){
        _traker._init("www.aoji.cn","108748"); 
		_traker.setMaxTierNum(1);
		_traker._monitorEvent("A", "id", "doyoo_mon_accept", "C", "onlineLY"); 
        if (typeof(convertclk)=="function"){
               convertclk();
        }
		script.onload=script.onreadystatechange=null;
	}
}
  
function _ca_listener_callback(target) {
	debugger;
	var _href = target.href;
	if(_href && _href !=null && _href.indexOf("new.jsp?companyID=8940") >=0 ) {
		//说明需要修改
		target.href = _href.replace("new.jsp?companyID=8940", "new.jsp?companyID=8940&sessionId=s1&uid=u1");
	}
//	var _onclick = target.onclick ;
//	if(_onclick && _onclick !=null && _onclick.indexOf("new.jsp?companyID=8940") >=0 ) {
//		target.setAttribute("onclick", _onclick.replace("new.jsp?companyID=8940", "new.jsp?companyID=8940&sessionId=s1&uid=u1"));
//	}
}

function convertclk() {
	_traker.attachMonitorCallBack(_ca_listener_callback);
   _traker._monitorEvent("A", "href","*http://www.b.com/new.jsp?companyID=8940", "C", "onlineLY");
   _traker._monitorEvent("A", "onclick","*http://www.b.com/new.jsp?companyID=8940", "C", "onlineLY");
}

