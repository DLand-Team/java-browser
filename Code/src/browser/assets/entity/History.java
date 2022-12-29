package browser.assets.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class History implements Serializable {

	private String time;
	private String fullUrl;
	private String host;
	private String title;

}
