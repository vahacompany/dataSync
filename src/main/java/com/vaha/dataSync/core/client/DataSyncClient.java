package com.vaha.dataSync.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public final class DataSyncClient implements Runnable {
	
	String sndMsg;
	
	public DataSyncClient(String str) {
		this.sndMsg = str;
	}
	
    public static void main(String[] args) throws Exception {
    	//DataSyncClient dataSyncClient = new DataSyncClient("client action!");
    	
    	//dataSyncClient.run();
    }

	@Override
	public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new DataSyncClientHandler(sndMsg));
                 }
             });

            ChannelFuture f = b.connect("localhost", 8023).sync();
            
            sndMsg = "client action!!!!!!!!!!!!!";
            
            f.channel().pipeline().addLast(new DataSyncClientHandler(sndMsg));
            
            f.channel().closeFuture().sync();
            
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally {
            group.shutdownGracefully();
        }
	}
    

}

