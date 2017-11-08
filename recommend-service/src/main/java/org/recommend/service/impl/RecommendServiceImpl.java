package org.recommend.service.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.common.utils.FileUtils;
import org.recommend.service.DataAssembleService;
import org.recommend.service.EmailService;
import org.recommend.service.RecommendService;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/11/02
 */
@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

	@Resource(name="dataAssembleService")
	private DataAssembleService dataAssembleService;
	
	@Resource(name="emailService")
	private EmailService emailService;
	
	public void recommend2Email(String email) {
//		String msgBodyTxt = dataAssembleService.assemblePlainTxt();
//		emailService.send(email,msgBodyTxt);
		
		File attachment = dataAssembleService.assembleExcel();
		try {
			emailService.sendWithAttachment(email,attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(attachment!=null){
				try {
					FileUtils.deleteTmpFile(attachment.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
