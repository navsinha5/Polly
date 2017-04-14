package com.amazonaws.demos.polly;

import java.io.InputStream;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class PollyEngineDemo {
	
	private AmazonPolly polly;
	private AdvancedPlayer player;
	private InputStream inputstream;
	private Voice voice;
	private String text;
	
	public void pollyPlay(String message) throws Exception{
	
	this.text = message;
	
	//create a polly client with default Credential and Region
	polly = AmazonPollyClientBuilder.defaultClient();
	
	//get available voice id
    DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
	DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
	voice = describeVoicesResult.getVoices().get(0);
    
	//synthesize speech
    SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(text)
    		.withVoiceId(voice.getId()).withOutputFormat(OutputFormat.Mp3);
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
    inputstream = synthRes.getAudioStream();
    
    //play with mp3 player
    player = new AdvancedPlayer(inputstream,
			javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
    player.play();
    
	}
}