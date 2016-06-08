# AI-Snake

* 整体架构

```
Controller
  - ControllerConfig
     - Drawer 绘制图形
     - Map 地图
	 - FoodMaker 生成食物
	 - Judge 检测游戏状态
	 - Snake 贪吃蛇实体
	    - Mind 控制贪吃蛇移动
```
* 默认是控制台下，如果想绘制到图形界面，实现Drawer接口并在Builder中设置

AI贪吃蛇，还不能吃满全地图，寻路算法还有缺陷
