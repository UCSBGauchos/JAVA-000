package top.yangbo.week03.nio.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author yangbo
 * @version 1.0
 * @className HttpRequestFilter
 * @description
 * @date 2020/10/31 1:43 PM
 **/
public interface HttpRequestFilter {
  void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx);
}
