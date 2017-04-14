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
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class PollyEngineDemo {
	
	public AmazonPolly polly;
	public static AdvancedPlayer player;
	public InputStream inputstream;
	private Voice voice;
	
	public PollyEngineDemo() throws Exception{
		
    polly = AmazonPollyClientBuilder.defaultClient();
	
    DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
	DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
	voice = describeVoicesResult.getVoices().get(0);
    
    SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText("Hi! Navdeep")
    		.withVoiceId(voice.getId()).withOutputFormat(OutputFormat.Mp3);
    SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
    inputstream = synthRes.getAudioStream();
    
    player = new AdvancedPlayer(inputstream,
			javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
	
	}

	
	public static void main(String[] args ) throws Exception{
		
	PollyEngineDemo demo = new PollyEngineDemo();	
		
	player.setPlayBackListener(new PlaybackListener() {
		@Override
		public void playbackStarted(PlaybackEvent evt) {
			System.out.println("Playback started");
		}
		
		@Override
		public void playbackFinished(PlaybackEvent evt) {
			System.out.println("Playback finished");
		}
	});		
	
	player.play();
	
	}
}
