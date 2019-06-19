$package('YiYa.parkReg');
$.extend($.fn.validatebox.defaults.rules, {
	loginName : {// 验证登录名称
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{1,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许2-16字节，允许字母数字下划线）'
	},
	loginPwd : {// 验证登录密码
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{1,15}$/i.test(value);
		},
		message : '密码不合法（字母开头，允许2-16字节，允许字母数字下划线）'
	},
	phone : { // 验证手机号
		validator : function(value) {
			return /^1[3-8]+\d{9}$/.test(value);
		},
		message : '请输入正确的手机号码。'
	},
	integer: {// 验证整数 
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
            //return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);//可正负数
        },
        message: '请输入整数'
    },
	CHS : {// 验证汉字或英文
		validator : function(value) {
			return /^([\u0391-\uFFE5]+)|(\w+[\w\s]+\w+)$/i.test(value)
		},
		message : "只能输入汉字或英文"
	},
	name : {// 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^([\u0391-\uFFE5]+)|(\w+[\w\s]+\w+)$/i.test(value)
		},
		message : '请输入姓名'
	},
	plateNum : {//验证车牌号码
		validator : function(value) {
			return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
		},
		message : '车牌号码无效（例：粤B12350）'
	},
	equalsNum : {//验证空余车位是否小于总车位
		validator: function (value, param) { 
			var start = $(param[0]).numberbox('getValue'); 
			alert(start)
			return value < start; 
			}, 
		message : '请输入正确的空余车位！'
	},
	isVip : {//验证是否为月卡用户
		validator : function(value) {
			return /是|否/.test(value);
		}, 
		message : '请输入是或否！'
	},
	
	
	
	
	
	idNumber : {// 验证身份证
		validator: function(value,param){    
            var flag= isCardID(value);  
            return flag==true?true:false;    
        },     
        message: '不是有效的身份证号码'   
	},

});



function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
    if(!/^((\d{17})|(\d{14}))(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误"; 
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   