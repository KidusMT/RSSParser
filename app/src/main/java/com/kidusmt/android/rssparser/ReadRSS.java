package com.kidusmt.android.rssparser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.HttpUrl;

/**
 * Created by KidusMT on 8/2/2017.
 */

public class ReadRSS extends AsyncTask<Void, Void, Void> {

    Context context;
    ProgressDialog progressDialog;
    String address = "http://feeds.bbci.co.uk/news/world/rss.xml";
    URL url;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public ReadRSS(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }


    @Override
    protected Void doInBackground(Void... params) {
        ProcessXML(GetData());
        return null;
    }

    private void ProcessXML(Document document) {
        if(document != null){
            feedItems = new ArrayList<>();
            Element root = document.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i< items.getLength();i++){
                Node currentChild = items.item(i);
                if(currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item = new FeedItem();
                    NodeList nodeChild = currentChild.getChildNodes();
                    for (int j = 0; j < nodeChild.getLength();j++){
                        Node current = nodeChild.item(j);
                        if(current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        }
                        else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            String url = current.getAttributes().item(2).getTextContent();
                            item.setThumbnail(url);
                        }
                        //Log.e("textContext: ",current.getTextContent());
                    }
                    feedItems.add(item);
//                    Log.i("itemTitle:",item.getTitle());
//                    Log.i("itemLink:",item.getLink());
//                    Log.i("itemDescription:",item.getDescription().toString());
//                    Log.i("itemPubDate:",item.getPubDate().toString());
                    Log.i("ïtemThumbnail:",item.getThumbnail());
                    Log.i(":","");
                }
            }

        }
    }

    public Document GetData(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
