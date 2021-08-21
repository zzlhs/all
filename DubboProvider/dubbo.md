## dubbo

### 负载均衡策略(在客户端配置，即引用的service上)

#### 1 Random  
随机，按权重设置随机概率。  
在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。  
#### 2 RoundRobin   
轮循，按公约后的权重设置轮循比率。  
存在慢的提供者累积请求问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
#### 3 LeastActive  
最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。  
使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大
#### 4 ConsistentHash
一致性Hash，相同参数的请求总是发到同一提供者。  
当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。  
算法参见：http://en.wikipedia.org/wiki/Consistent_hashing。  
缺省只对第一个参数Hash，如果要修改，请配置<dubbo:parameter key="hash.arguments" value="0,1" />  
缺省用160份虚拟节点，如果要修改，请配置<dubbo:parameter key="hash.nodes" value="320" />  

# 去这里看吧很详细
https://blog.csdn.net/hardworking0323/article/details/81293402?ops_request_misc=%257B%2522request