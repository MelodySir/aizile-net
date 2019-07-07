package ai.zile.lib.net.filter;

import ai.zile.lib.net.config.ConfigKeys;
import ai.zile.lib.net.config.Configurator;
import ai.zile.lib.net.filter.chain.RealRespChain;
import ai.zile.lib.net.filter.data.RespData;

import java.util.ArrayList;
import java.util.List;

public class RespFilterManager {

    /**
     * 执行 网络请求 返回数据 的分类处理 解耦框架和业务层
     */
    public static RespData execute(RespData data) throws Exception {

        List<RespFilter> filters = new ArrayList<>();
        List<RespFilter> clientFilters = Configurator.getInstance().getConfiguration(ConfigKeys.NET_FILTER);

        filters.add(new RespBaseFilter());
        if (null != clientFilters && clientFilters.size() > 0) {
            filters.addAll(clientFilters);
        }
        RespFilter.RespChain realRespChain = new RealRespChain(filters, data, 0);
        return realRespChain.proceed(data);
    }

}
