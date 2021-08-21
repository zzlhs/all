package com.zzl.dubbo.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

import lombok.Data;

@Component
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER})
@Data
public class ValidationFilter implements Filter {
	
	private FacadeAccessConfig facadeAccessConfig;
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Result result = null;
        // 获取调用的接口名
        String reqFacade = invoker.getInterface().getSimpleName();
        // 尝试在白名单配置文件里查找定义的接口，如果找不到则catch住异常、并许可访问。
    	String[] fundIps = facadeAccessConfig.getFundURLs().split(" ");
    	List fundIpsList = Arrays.asList(fundIps);
    	
    	// 获取remoteAddress:进行访问的应用，格式ip:port
    	String remoteAddress = RpcContext.getContext().getRemoteAddressString();
        // 只取ip
        String remoteIp = remoteAddress.split(":")[0];
    	if(fundIpsList.contains(remoteIp)) {
    		 // 无特殊限制，则许可访问
            result = invoker.invoke(invocation);
            return result;
    	}else {
    		  // 权限不许可、退出访问
            result = new RpcResult("remoteAddress" + remoteAddress + "无权访问接口" + reqFacade);
            return result;
		}
		
	}
}
