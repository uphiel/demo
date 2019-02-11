package com.wangfei.thread;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable {
    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName){
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(initPath);
        if(file.isDirectory()){
            try{
                directoryProcess(file);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    //目录检索
    private void directoryProcess(File file) throws InterruptedException{
        File[] list = file.listFiles();
        if(list != null){
            for(int i = 0; i < list.length; i++){
                if(list[i].isDirectory()){
                    directoryProcess(list[i]);
                }else{
                    fileProcess(list[i]);
                }
            }
        }
        if(Thread.interrupted()){
            throw new InterruptedException();
        }
    }
    //文件检索
    private void fileProcess(File file) throws InterruptedException{
        if(fileName.equals(file.getName())){
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        if(Thread.interrupted()){
            throw new InterruptedException();
        }
    }
    public static void main(String[] args){
        FileSearch searcher=new FileSearch("D:\\","autoexec.bat");
        Thread thread=new Thread(searcher);
        thread.start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread.interrupt();
    }

}
