package com.sproutsignal.app;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.ValueCallback;
import android.webkit.DownloadListener;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Environment;
import android.webkit.URLUtil;

public class MainActivity extends Activity {
  private WebView web;
  private ValueCallback<Uri[]> pendingFiles;
  private static final int PICK_FILE = 1001;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    web = new WebView(this); setContentView(web);
    WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
    s.setAllowFileAccess(true); s.setAllowContentAccess(true);
    web.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, android.webkit.WebResourceRequest req) {
        Uri uri=req.getUrl();
        if ("file".equals(uri.getScheme())) return false;
        try {startActivity(new Intent(Intent.ACTION_VIEW,uri));}catch(Exception ignored){}
        return true;
      }
    });
    web.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView w, ValueCallback<Uri[]> callback, FileChooserParams params){
        if(pendingFiles!=null) pendingFiles.onReceiveValue(null);
        pendingFiles=callback;
        try {startActivityForResult(params.createIntent(),PICK_FILE);return true;}
        catch(Exception e){pendingFiles=null;return false;}
      }
    });
    web.setDownloadListener((url,userAgent,contentDisposition,mimeType,contentLength)->{
      try {
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setMimeType(mimeType);request.addRequestHeader("User-Agent",userAgent);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url,contentDisposition,mimeType));
        ((DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
      }catch(Exception ignored){}
    });
    web.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(requestCode==PICK_FILE && pendingFiles!=null){
      pendingFiles.onReceiveValue(resultCode==RESULT_OK?WebChromeClient.FileChooserParams.parseResult(resultCode,data):null);
      pendingFiles=null;
    }
  }
  @Override public void onBackPressed(){if(web!=null && web.canGoBack())web.goBack();else super.onBackPressed();}
}
