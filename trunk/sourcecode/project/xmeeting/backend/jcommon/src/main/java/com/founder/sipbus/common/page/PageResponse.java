package com.founder.sipbus.common.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author cw peng
 *
 * @param <E>
 */
public class PageResponse<E> {
	private long totalCount;
	private long numPerPage;
	private long pageNumShown;
	private long currentPage;
	private List<E> list;

	public PageResponse(long totalCount, List<E> list) {
		super();
		this.totalCount = totalCount;
		this.list = list;
	}

	public PageResponse() {
		super();
		this.totalCount = 0;
		this.list = new ArrayList<E>();
	}
	
	public PageResponse(PageRequest pageRequest) {
		super();
		this.totalCount = 0;
		this.list = new ArrayList<E>();
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public long getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(long numPerPage) {
		this.numPerPage = numPerPage;
	}

	public long getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(long pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

}
