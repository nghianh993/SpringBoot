package fis.htkh.models;

public class AjaxResultModel<T> {
	
    private T resultData;    

    private String message;

    private boolean result;

    private long numberRecord;
    
    private long pageIndex;

	private long pageSize;
	
	private long code;

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public long getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(long numberRecord) {
		this.numberRecord = numberRecord;
	}

	public long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}
}