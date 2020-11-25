

## gitee下载的github项目关联到github远程库/pr操作笔记

- 移除关联gitee，添加github关联

```powershell
//查看所有远程库
PS D:\opt\shardingsphere> git remote -v
origin  https://gitee.com/wangwenjie1993/shardingsphere.git (fetch)
origin  https://gitee.com/wangwenjie1993/shardingsphere.git (push)

//删除所有远程库
PS D:\opt\shardingsphere> git remote rm origin

//添加github远程库
PS D:\opt\shardingsphere> git remote add origin https://github.com/wangwenjie666/shardingsphere.git

//查看 确定已关联
PS D:\opt\shardingsphere> git remote -v
origin  https://github.com/wangwenjie666/shardingsphere.git (fetch)
origin  https://github.com/wangwenjie666/shardingsphere.git (push)
```

- 关联上游仓库

上面的操作仅仅关联了自己fork的shardingsphere代码的仓库，如果要提交pr则需要关联上游仓库

```powershell
//添加上游仓库
PS D:\opt\shardingsphere> git remote add upstream https://github.com/apache/shardingsphere.git

//查看信息
PS D:\opt\shardingsphere> git remote -v
origin  https://github.com/wangwenjie666/shardingsphere.git (fetch)
origin  https://github.com/wangwenjie666/shardingsphere.git (push)
upstream        https://github.com/apache/shardingsphere.git (fetch)
upstream        https://github.com/apache/shardingsphere.git (push)
```

- 创建提交pr的分支（后续操作用自己项目做得测试）

```
 git checkout -b issue#1
```

- 提交代码到远程分支

```
 git add .
 git commit -m "xxx"
 git push origin issue#1
```

- 在github浏览器可以看到pr的提示，并且多了一条分支（issue#1）

![](.\pr.png)

- 在网页上执行后续pr操作（批注，提交即可将 ` issue#1 ` 分支内容合并到 ` main ` 分支）





注：**git文件名过长问题：**git config --global core.longpaths true