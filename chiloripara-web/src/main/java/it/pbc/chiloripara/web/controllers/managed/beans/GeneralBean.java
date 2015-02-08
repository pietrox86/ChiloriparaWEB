package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.web.utls.FaceMessagesUtil;
import it.pbc.chiloripara.web.utls.LabelUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneralBean {

	public Logger getLogger() {
		return logger;
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected FaceMessagesUtil faceMsg;
	@Autowired
	protected LabelUtils labels;

}
