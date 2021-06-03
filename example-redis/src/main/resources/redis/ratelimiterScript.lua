
local key = KEYS[1]   --限流KEY
local limitCount = tonumber(ARGV[1])       --限流大小
local limitTime = tonumber(ARGV[2])        --限流时间
local current = redis.call('get', key);
print(current)
if current then
    if current + 1 > limitCount then --如果超出限流大小
        return 0
    else
        redis.call("INCRBY", key,"1")
        return current + 1
    end
else
    redis.call("set", key,"1")
    redis.call("expire", key,limitTime)
    return 1
end