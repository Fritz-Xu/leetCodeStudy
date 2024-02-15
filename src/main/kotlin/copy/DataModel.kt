package copy

data class DataModel(val name: String, val location: String, val type: DataType, var virtual: Boolean = false) {

    var sceneId = -1
    var deviceId = -1


    sealed class DataType {
        class Music : DataType()
        class Device : DataType()
        class Scene : DataType()
        class Hub : DataType()
    }
}

fun defaultData(): List<DataModel> {
    return mutableListOf<DataModel>().apply {
        add(DataModel("龙卷风", "music", DataModel.DataType.Music(), false))
        add(DataModel("对讲", "hub", DataModel.DataType.Hub(), false))
        add(DataModel("物业", "hub", DataModel.DataType.Hub(), false))
        add(DataModel("回家模式", "-1", DataModel.DataType.Scene(), true))
        add(DataModel("晚安模式", "-1", DataModel.DataType.Scene(), true))
        add(DataModel("吊灯", "客厅", DataModel.DataType.Device(), true))
        add(DataModel("夜灯", "客厅", DataModel.DataType.Device(), true))
        add(DataModel("彩灯", "客厅", DataModel.DataType.Device(), true))
        add(DataModel("空调", "客厅", DataModel.DataType.Device(), true))
    }
}

fun loadData(): List<DataModel> {
    return mutableListOf<DataModel>().apply {
        add(DataModel("龙卷风", "music", DataModel.DataType.Music()))
        add(DataModel("可视对讲", "hub", DataModel.DataType.Hub()))
        add(DataModel("呼叫物业", "hub", DataModel.DataType.Hub()))
        add(DataModel("低碳管家", "hub", DataModel.DataType.Hub()))
        add(DataModel("用水管家", "hub", DataModel.DataType.Hub()))
        add(DataModel("安防管家", "hub", DataModel.DataType.Hub()))
        add(DataModel("消息中心", "hub", DataModel.DataType.Hub()))
        add(DataModel("相册预览", "hub", DataModel.DataType.Hub()))
        add(DataModel("家庭留言", "hub", DataModel.DataType.Hub()))
        add(DataModel("回家001", "-1", DataModel.DataType.Scene()).apply {
            sceneId = 100
        })
        add(DataModel("晚安002", "-1", DataModel.DataType.Scene()).apply {
            sceneId = 101
        })
        add(DataModel("回家004", "-1", DataModel.DataType.Scene()).apply {
            sceneId = 102
        })
        add(DataModel("晚安005", "-1", DataModel.DataType.Scene()).apply {
            sceneId = 103
        })
    }
}

fun checkSameType(item: DataModel, compare: DataModel): Boolean {
    return item.type == compare.type
}

fun main() {
    val dataTotal = loadData()
    val dataEdit = defaultData()
    //1.替换掉虚拟model
    //2.如果非虚拟model,但在dataTotal里面找不到,那么执行删除
    dataTotal.filter { data ->
//        if (data.type is DataModel.DataType.Music || data.type is DataModel.DataType.Hub) {
//            true
//        }
        if (data.type is DataModel.DataType.Scene) {
           val edit=  dataEdit.firstOrNull { edit -> edit.deviceId == data.deviceId }
            edit == null
        }else if (data.type is DataModel.DataType.Device) {
            true
        } else {
            true
        }
    }
}