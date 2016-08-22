package ren.maichu.portal.model;

public class ReturnVO {

	public static final int CODE_SUCCESS = 1;
	public static final int CODE_FAIL = 0;
	public static final int CODE_NOT_LOGIN = -1;
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_FAIL = "操作失败";
	public static final String MSG_NOT_LOGIN = "没有登录";

	int code;
	String msg;
	String errorMsg;
	Object content;

	public ReturnVO(){

	}


	public ReturnVO(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ReturnVO(int code, String msg, String errorMsg) {
		super();
		this.code = code;
		this.msg = msg;
		this.errorMsg = errorMsg;
	}
	
	public ReturnVO(int code, String msg, Object content) {
		super();
		this.code = code;
		this.msg = msg;
		this.content = content;
	}

	public ReturnVO(int code, String msg, String errorMsg, Object content) {
		super();
		this.code = code;
		this.msg = msg;
		this.errorMsg = errorMsg;
		this.content = content;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
