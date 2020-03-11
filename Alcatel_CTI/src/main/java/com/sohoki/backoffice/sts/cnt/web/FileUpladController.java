package com.sohoki.backoffice.sts.cnt.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.service.Globals;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfo;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfoVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;


//thumnail 관련 작업
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;

//동영상 이미지 캡처ㅜ
//mp3
import org.tritonus.share.sampled.file.TAudioFileFormat;

@Controller
public class FileUpladController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUpladController.class);
	
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	    
    @Resource(name="ContentFileInfoManageService")
	private ContentFileInfoManageService conFileService;
    
    @Resource(name="egovFileIdGnrService")
	private EgovIdGnrService egovFileIdGnrService;    
    
    
	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    public String dragAndDrop(Model model) {         
        return "fileUpload";         
    }
	

	@RequestMapping(value = "/backoffice/upload/fileUpload.do") //ajax에서 호출하는 부분
	@ResponseBody
    public Map upload(MultipartHttpServletRequest multipartRequest
    		                       , HttpServletRequest request
    		                       ) { //Multipart로 받는다.
         
		LOGGER.debug("upload start");
        String filePath = propertiesService.getString("Globals.fileStorePath") ;        
        String Message = "";
        Map<String, String> map = new HashMap<String, String>();
        
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
             map.put("message", "fail Login Error");
        }else {
        	Iterator<String> itr =  multipartRequest.getFileNames();
            
        	 while (itr.hasNext()) { //받은 파일들을 모두 돌린다.
                 
                 
                 MultipartFile mpf = multipartRequest.getFile(itr.next());      
                 String originalFilename = mpf.getOriginalFilename(); //파일명
                 String fileFullPath = "";
                 String Ext = "";        	      
                 
                 try {
                 	//디렉톨 생성 여부 확인 
                 	String inDate   = new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());            	
                 	File filedir = new File(filePath+"/"+inDate);
                 	
                 	if (!filedir.isDirectory()){
                 		filedir.mkdir();
                 	}
                     //파일 저장
                 	fileFullPath = filePath + "/"+inDate+"/" + originalFilename;
                 	LOGGER.debug("fileFullPath:" + fileFullPath);
                 	File file_s =  new File(fileFullPath );            	
                 	Ext = fileExt(file_s,".");    
                 	LOGGER.debug("Ext:" + Ext);
                 	String atchFileId = egovFileIdGnrService.getNextStringId();
                 	file_s = rename(file_s, atchFileId+"."+Ext);            	
                    mpf.transferTo(file_s); //파일저장 실제로는 service에서 처리                
                     
                     
                     String  thumnail = null;
                     // DB 에 저장
                     //동영상 파일시 썸네일 파일 생성 
                     if (fileExt(file_s,".").equals("mp4") || fileExt(file_s,".").equals("avi") || fileExt(file_s,".").equals("webm") ){
                     	thumnail = getImageFromFrame(file_s.toString(),  filedir.toString());
                 	}
                 	
                    
                     ContentFileInfo vo = new ContentFileInfo();
                     
                     vo.setAtchFileId(atchFileId);
                     if (thumnail != null && !thumnail.equals("")&& !thumnail.equals("Fail") ){
                         String[] fileInfos = 	thumnail.split(":");
                     	 vo.setFileThumnail(fileInfos[0].toString()  );
                     	 vo.setPlayTime(fileInfos[1].toString());        
                     }
                     
                    vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString()); 
     				vo.setFileStreCours(filePath+"/"+inDate+"/");
             		vo.setStreFileNm(file_s.getName());
             		vo.setOrignlFileNm(originalFilename);
             		vo.setFileExtsn(fileExt(file_s,"."));        		
             		vo.setFileSize( fileSize(  file_s ));
                    vo.setFileOrder( Integer.toString( Integer.parseInt(atchFileId.replace("FILE_",""))));



                    ContentFileInfoVO contentFileInfoVO = new ContentFileInfoVO();
                    contentFileInfoVO.setStreFileNm(file_s.getName());
                    contentFileInfoVO.setOrignlFileNm(originalFilename);
                    contentFileInfoVO.setSearchKeyword(file_s.getName());
                    contentFileInfoVO.setSearchCondition("atchFileId");
                    contentFileInfoVO.setMediaType(modifyExtension(originalFilename));
                     
                    if(contentFileInfoVO.getMediaType().equals("MEDIA")){
                     	contentFileInfoVO.setNotConType("MUSIC");
                     	vo.setReportGubun("reportGubun_4");
                    }else if (contentFileInfoVO.getMediaType().equals("IMAGE")){
                     	contentFileInfoVO.setNotConType("MUSIC");
                     	vo.setReportGubun("reportGubun_3");
                    }
                     
                    if(conFileService.selectFilePageListByPaginationTotCnt_S(contentFileInfoVO) < 1) {
                     	conFileService.insertFileManage(vo);
                     	map.put("message", "success");
                     	map.put("originalFilename", originalFilename);
                    } else {
                     	map.put("message", "fail");
                     	map.put("originalFilename", originalFilename);
                    }
                 } catch (Exception e) {                
                     e.printStackTrace();
                     map.put("message", "fail");
                 }
                 
            }
        }
        
        
        return map;
    }
	//mp3 재생시간 
	private static String getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {

	    AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
	    
	    String mp3duration = null;
	    
	    if (fileFormat instanceof TAudioFileFormat) {
	        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
	        String key = "duration";
	        Long microseconds = (Long) properties.get(key);
	        int mili = (int) (microseconds / 1000);
	        int sec = (mili / 1000);
	        int sec_M = (mili / 1000) % 60;
	        int min = (mili / 1000) / 60;
	        
	        mp3duration = Integer.toString(sec)+"/"+ Integer.toString(min)+":"+ Integer.toString(sec_M);
	        
	    } else {
	    	mp3duration = "F";
	        throw new UnsupportedAudioFileException();	        
	    }	    
	    return mp3duration;

	}
	public String fileSize(File f){
		String fileSize = "";
		if (f.exists()){
			fileSize =  Long.toString(f.length());
		}else {
			fileSize = "0";
		}
		return fileSize;
	}
	
	@RequestMapping(value="/backoffice/popup/fileView.do")
	public String selectFileView (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
			                                   , ContentFileInfoVO vo
								               , HttpServletRequest request
											   , BindingResult bindingResult
											   , ModelMap model) throws  Exception{	
		
		
		
		String atchFileId = request.getParameter("atchFileId") != null ? request.getParameter("atchFileId") : "";
		LOGGER.debug("atchFileId:" + atchFileId);
		model.addAttribute("regist",  conFileService.selectFileDetail(atchFileId));				
		return "/backoffice/popup/FileView";
	}
	@RequestMapping(value="/backoffice/sub/conManage/conFileUpdate.do")
	@ResponseBody
	public String conMutiDetail ( @ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												 , ContentFileInfoVO vo
									             , HttpServletRequest request
												   , BindingResult bindingResult
												   , ModelMap model)throws Exception{
		String atchFileId = request.getParameter("atchFileId") != null ? request.getParameter("atchFileId") : "";
		String playTime = request.getParameter("playTime") != null ? request.getParameter("playTime") : "";
		String fileWidth = request.getParameter("fileWidth") != null ? request.getParameter("fileWidth") : "";
		String fileHeight = request.getParameter("fileHeight") != null ? request.getParameter("fileHeight") : "";
		
		
		vo.setAtchFileId(atchFileId);
		if (!playTime.equals("") && playTime != null){
		    vo.setPlayTime(Integer.toString((int)( Double.parseDouble( playTime))));
		}
		vo.setFileWidth(fileWidth);
		vo.setFileHeight(fileHeight);
		String delResult = "";
		
		
		System.out.println("consize ? " + fileWidth + " * " + fileHeight);
		
		int ret = conFileService.updateFileDetailInfo(vo) ;
		if (ret > 0){
			delResult="O";   	
		}else {
			delResult="F";
		}		
		return delResult; 
		
		
	}
	
	//썸네일 이미지 
	private static String getImageFromFrame(String _fileNm, String _filePath) {
		
		
		
	    Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));        
        String thumnailFull = _filePath+ "/"+ today+"_thumnail.png";
        String thumnail = today+"_thumnail.png";
		
		String fileName = _fileNm;// videoFile.getAbsolutePath();
		String baseName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
		String savePath = fileName.substring(0, fileName.lastIndexOf("/"));
		String duration = "";
		double frameNumber = 0d;
		try {
			File videoFile = new File(fileName);
			
			SeekableByteChannel bc = NIOUtils.readableFileChannel(videoFile);
			MP4Demuxer dm = new MP4Demuxer(bc);
			DemuxerTrack vt = dm.getVideoTrack();
			frameNumber = vt.getMeta().getTotalDuration() / 5.0;
			duration =  Double.toString(vt.getMeta().getTotalDuration()) ;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Picture frame = FrameGrab.getNativeFrame(new File(fileName), frameNumber);
			BufferedImage img = AWTUtil.toBufferedImage(frame);
			File pngFile = new File(thumnailFull);
			if (!pngFile.exists()) {
				pngFile.createNewFile();
			}
			ImageIO.write(img, "png", pngFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JCodecException e) {
			e.printStackTrace();
		}
		
		return thumnail+":"+duration;

	}
	private String fileExt(File f, String _seq){
		String ext = "";
		 int dot =  f.getName().lastIndexOf(_seq);
		 if (dot != -1) {                              //확장자가 없을때		
		      ext =  f.getName().substring(dot + 1);
		} else {                                     //확장자가 있을때		      
		      ext = "";
		}
		return ext;
	}
	public File rename(File f, String fileNm) {             //File f는 원본 파일
	 
		//if (createNewFile(f)) return f;        //생성된 f가 중복되지 않으면 리턴
		
	    String name = f.getName();
	    String body = null;
	    String ext = null;
	    
	    
	    int dot = name.lastIndexOf(".");
	    LOGGER.debug("dot:"+ dot);
	    LOGGER.debug("fileNm:"+ fileNm + "indexof" + fileNm.lastIndexOf("."));
	    
	    if (dot != -1) { //확장자 있을떄 
		    body  = fileNm.substring(0, fileNm.lastIndexOf(".")) ;
		    ext = fileNm.substring(fileNm.lastIndexOf("."), fileNm.length());
	    } else {  //확장자가 없을떄      
	    	body = fileNm;
		    ext = "";
	    }	
	    
	    int count = 0;
	    //중복된 파일이 있을때
	    //파일이름뒤에 a숫자.확장자 이렇게 들어가게 되는데 숫자는 9999까지 된다.
	    
	    if ( fileExites(body) && count == 0   ) {
	    	  String newName = body + ext;
		      f = new File(f.getParent(), newName);
		      LOGGER.debug("파일이 없을때  filenm:"+ newName);
	    }else {	    
		    while (!createNewFile(f) && count < 9999) {  
		      count++;
		      String newName = body+"(" + count +")" + ext;
		      LOGGER.debug("파일이 있을때  filenm:"+ newName);
		      f = new File(f.getParent(), newName);
		    }
	    }
	    return f;
	 }
	//파일 업로드 확인 
	public  String uploadFileNm(List<MultipartFile> mpf, String filePath){
		
		String fileNm = "";
		String ext = "";
        try {
        	
        	for (MultipartFile mFile : mpf) {
        		
        		
	            String originalFilename = mFile.getOriginalFilename(); //파일명	   
	            if (!originalFilename.equals("")){
	            	String fileFullPath = filePath + "/" + originalFilename;
		        	File file_s =  new File(fileFullPath);	        	
		        	
		        	file_s = rename(file_s, file_s.getName().toString());
		        	mFile.transferTo(file_s);
					fileNm = file_s.getName();	
	            }
        	}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.debug("uploadFileNm IllegalStateException :" + e.toString());
			fileNm = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.debug("uploadFileNm IOException :" + e.toString());
			fileNm = "";
		} catch (Exception ex){			
			LOGGER.debug("uploadFileNm Exception :" + ex.toString());
			fileNm = "";
		}        
		return fileNm;
	}
    //파일 생성 
	private boolean createNewFile(File f) { 
	    try {
	      return f.createNewFile();                        //존재하는 파일이 아니면
	    }catch (IOException ignored) {
	      return false;
	    }
	}
	
	private boolean fileExites( String fileNm){
		try{
			File f = new File (propertiesService.getString("Globals.fileStorePath")+"/"+fileNm);
			if (f.exists()){
				return false;
			}else {
				return true;
			}
		}catch (Exception e){
			LOGGER.debug("fileExites Error:" + e.toString());
			return false;
		}
		
	}
	
	//파일 삭제 
    public  boolean deleteFile (String fileNm ) {
    	try{        		        	
    		File delFile = new File ( fileNm);
    		delFile.delete();
    		return true;    		
    	}catch(Exception e){
    		LOGGER.debug("file Delete error{0}", e.toString());
    		return false;
    	}    	
    }
    // 파일 확장자 변환
    private String modifyExtension(String extension){
    	LOGGER.info("extension : " +  extension);
    	String type = extension.split("\\.")[1].toString().toLowerCase();
    	LOGGER.info("type : " + type);
    	if("mp4".equals(type) || "avi".equals(type) || "mpeg".equals(type)) {
    		extension = "MEDIA";
		} else if("jpg".equals(type) || "jpeg".equals(type) || "gif".equals(type) || "png".equals(type) || "bmp".equals(type)) {
			extension = "IMAGE";
		} else if("mp3".equals(type) || "wav".equals(type) || "mid".equals(type)) {
			extension = "MUSIC";
		} else {
			extension = "MUSIC";
		}
    	return extension;
    }    
	
}
