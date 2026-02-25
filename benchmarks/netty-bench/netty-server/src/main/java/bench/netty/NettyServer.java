package bench.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public final class NettyServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("port", "8081"));
        int bossThreads = Integer.parseInt(System.getProperty("bossThreads", "1"));
        int workerThreads = Integer.parseInt(System.getProperty("workerThreads", String.valueOf(Runtime.getRuntime().availableProcessors())));

        EventLoopGroup boss = new NioEventLoopGroup(bossThreads);
        EventLoopGroup worker = new NioEventLoopGroup(workerThreads);

        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(1 << 20));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
                                    byte[] body = "OK".getBytes(StandardCharsets.UTF_8);

                                    FullHttpResponse resp = new DefaultFullHttpResponse(
                                            HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(body)
                                    );
                                    resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8");
                                    resp.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, body.length);
                                    resp.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

                                    boolean keepAlive = HttpUtil.isKeepAlive(req);
                                    if (keepAlive) {
                                        ctx.writeAndFlush(resp);
                                    } else {
                                        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
                                    }
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    });

            Channel ch = b.bind(port).sync().channel();
            System.out.println("Netty listening on http://127.0.0.1:" + port + "/");
            ch.closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}