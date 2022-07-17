package com.kits.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseFile {
	private String name;
	private String url;
	private long size;
}
