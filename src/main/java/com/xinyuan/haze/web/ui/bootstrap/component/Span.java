package com.xinyuan.haze.web.ui.bootstrap.component;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.web.ui.bootstrap.css.SpanType;

public class Span implements Component{

	private String id;
	
	private SpanType spanType;
	
	private String text;
	
	public Span(String text) {
		super();
		spanType = SpanType.DEFAULT;
		this.text = text;
	}
	
	
	public Span(String id, String text) {
		super();
		this.id = id;
		spanType = SpanType.DEFAULT;
		this.text = text;
	}


	public Span(String id, SpanType spanType, String text) {
		super();
		this.id = id;
		this.spanType = spanType;
		this.text = text;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SpanType getSpanType() {
		return spanType;
	}

	public void setSpanType(SpanType spanType) {
		this.spanType = spanType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getHtml() {
		StringBuffer span = new StringBuffer("<span ");
		if (HazeStringUtils.isNotEmpty(id)) {
			span.append("id='");
			span.append(id);
			span.append("' ");
		}
		span.append("class='" + SpanType.DEFAULT.getMessageValue());
		if (SpanType.DEFAULT != spanType ) {
			span.append(" " + spanType.getMessageValue() + "'");
		} else {
			span.append("'");
		}
		span.append(">");
		if (HazeStringUtils.isNotEmpty(text)) {
			span.append(text);
		}
		span.append("</span>");
		return span.toString();
	}


	@Override
	public void addClass(String className) {
		
	}

}
