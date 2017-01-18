package monash.edu.hally.main;

import monash.edu.hally.index.Documents;
import monash.edu.hally.model.JSTModel;
import monash.edu.hally.model.TUSLDAModel;
import monash.edu.hally.nlp.FilesUtil;

public class Main {
	
	public void start()
	{
		long startTime=System.currentTimeMillis();
		//�������������Ͽ��������ĵ�
		Documents docs=new Documents();
		docs.indexAllDocuments();
		
		
//		JSTModel jstModel=new JSTModel(docs);
//		jstModel.initialiseModel();
//		//�ƶϺͱ���ģ�͵�Ǳ�ڱ���
//		jstModel.inferenceModel();
		
		//ѵ��ģ��
		TUSLDAModel tsldaModel=new TUSLDAModel(docs);
//		tsldaModel.initialiseModel2(false);
		tsldaModel.initialiseModel();
		//�ƶϺͱ���ģ�͵�Ǳ�ڱ���
		tsldaModel.inferenceModel();
		
		long endTime=System.currentTimeMillis();
		System.out.println("Runtime "+(endTime-startTime)/1000+"s.");
		
		FilesUtil.printSuccessMessage();
	}

	public static void main(String[] args) {
		
		new Main().start();
	}

}
