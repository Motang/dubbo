/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.monitor.simple.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.dubbo.common.Extension;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.container.web.Menu;
import com.alibaba.dubbo.container.web.Page;
import com.alibaba.dubbo.container.web.PageHandler;
import com.alibaba.dubbo.monitor.simple.RegistryContainer;

/**
 * ServicesPageHandler
 * 
 * @author william.liangf
 */
@Menu(name = "Services", desc = "Services", order = 1000)
@Extension("services")
public class ServicesPageHandler implements PageHandler {
    
    public Page handle(URL url) {
        Set<String> services = RegistryContainer.getInstance().getServices();
        List<List<String>> rows = new ArrayList<List<String>>();
        int providerCount = 0;
        int consumerCount = 0;
        if (services != null && services.size() > 0) {
            for (String service : services) {
                List<String> row = new ArrayList<String>();
                row.add(service);
                List<URL> providers = RegistryContainer.getInstance().getProviders(service);
                int providerSize = providers == null ? 0 : providers.size();
                providerCount += providerSize;
                row.add("<a href=\"providers.html?service=" + service + "\">Providers(" + providerSize + ")</a>");
                List<URL> consumers = RegistryContainer.getInstance().getConsumers(service);
                int consumerSize = consumers == null ? 0 : consumers.size();
                consumerCount += consumerSize;
                row.add("<a href=\"consumers.html?service=" + service + "\">Consumers(" + consumerSize + ")</a>");
                row.add("<a href=\"statistics.html?service=" + service + "\">Statistics</a>");
                rows.add(row);
            }
        }
        return new Page("Services", "Services (" + rows.size() + ")",
                new String[] { "Service Name:", "Providers(" + providerCount + ")", "Consumers(" + consumerCount + ")", "Statistics" }, rows);
    }

}