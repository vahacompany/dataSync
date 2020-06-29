package com.vaha.dataSync.core.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public final class DataSyncServer {

	Logger logger = LoggerFactory.getLogger(DataSyncServer.class);

	@Value("${boss.thread.count}")
	private int boss;

	@Value("${worker.thread.count}")
	private int worker;

	@Value("${tcp.port}")
	private int tcpPort;

	private InetSocketAddress address;

	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;

	public void start() {

		bossGroup = new NioEventLoopGroup(boss);
		workerGroup = new NioEventLoopGroup(worker);

		try {
			address = new InetSocketAddress(tcpPort);

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new DataSyncInitializer());

			if (logger.isDebugEnabled()) {
				logger.debug("address === " + address.getPort());
			}
			
			ChannelFuture future = b.bind(address).sync();

			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public void shutdown() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		address = null;
	}
}