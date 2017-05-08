package br.com.trixti.itm.infra.sms;

public enum SMSZenviaStatusCodeEnum {

	OK("00"),
	SCHEDULED("01"),
	SENT("02"),
	DELIVERED("03"),
	NOT_RECEIVED("04"),
	BLOCKED_NOT_COVERAGE("05"),
	BLOCKED_BLACK_LISTED("06"),
	BLOCKED_INVALID_NUMBER("07"),
	BLOCKED_CONTENT_NOT_ALLOWED("08"),
	BLOCKED_MESSAGE_EXPIRED("09"),
	BLOCKED("10"),
	ERROR("11"),

	ACCOUNT_LIMITE_REACHED("990");

	private String codigo;

	private SMSZenviaStatusCodeEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

}