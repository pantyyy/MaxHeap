
//堆里面的元素需要有比较性 , 所以需要是Comparable的子类
public class MaxHeap<E extends Comparable<E>> {
    //实现最大堆底层的数据结构 , 数组
    private Array<E> data;

    //构造函数

    //已知堆的容量
    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }


    //传入一个数组 , 将这个数组进行最大堆化
    //方法1 : 将这个数组中的每个元素放入这个堆中 ,调用add()方法
    //方法2 : 从第一个非叶子节点开始 , 进行下沉操作 , 直到最大的那个元素
    public MaxHeap(E[] arr){
        data = new Array<>(arr);
        //如果定位第一个非叶子节点?
        //第一个非叶子节点 = 最后一个节点的父节点
        for(int i = parent(data.getSize() - 1) ; i >= 0 ; i--){
            siftDown(i);
        }
    }


    //不知道容量
    public MaxHeap(){
        data = new Array<>();
    }

    //返回堆中元素的个数
    public int size(){
        return data.getSize();
    }

    //堆是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //返回一个节点的父节点的索引
    public int parent(int index){
        //判断index的合法性
        if (index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return ( index - 1 ) / 2;
    }

    //返回左孩子索引
    public int left(int index){
        return index * 2 + 1;
    }

    //返回右孩子索引
    public int right(int index){
        return index * 2 + 2;
    }


    //向堆中添加元素
    public void add(E e){
        //1.在完全二叉树的末尾添加节点等于在数组的最后添加元素
        data.addLast(e);
        //2.添加元素之后可能打破了最大堆的规则 , 需要进行元素的上浮
        siftUp(data.getSize() - 1);
    }

    //传入当前节点的索引 , 进行上浮操作
    private void siftUp(int i) {
        //传入的i需要大于0 , 因为如果i = 0 , 已经不能向上浮动了
        //判断是否需要上浮
        while(i > 0 && data.get(i).compareTo(data.get(parent(i))) > 0){
            //当前元素大于父元素 , 需要上浮
            //交换元素中的值
            data.swap(i , parent(i));
            //更新i的值 , 为下一轮比较作准备
            i = parent(i);
        }

    }

    //返回最大节点
    public E findMax(){
        if(data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty.");
        return data.get(0);
    }

    //取出堆中的最大值
    public E extractMax(){
        E ret = findMax();

        //1.最大值和最后一个元素交换
        data.swap(0 , data.getSize() - 1);
        //2.移除最后一个元素 , 即交换过去的最大值
        data.removeLast();
        //3.进行下浮 , 完成最大堆的调整
        siftDown(0);

        return ret;
    }

    private void siftDown(int i) {
        //比较i和i的左右孩子 , 找到这三个值中的最大值来当父节点


        while(left(i) < data.getSize()){//进行下浮的条件 , 左孩子不为空
            int j = left(i);    //左孩子的索引

            if(j + 1 < data.getSize()) {//判断右孩子是否为空
                //右孩子不为空
                //左右孩子比较 , 选择比较大的那个索引
                if(data.get(j).compareTo(data.get(j + 1)) < 0)
                    j++;
            }

            //经过上面的操作 , j所在索引就是比较大的那个孩子节点
            //j代表的节点和i比较 , 判断是否需要交换值
            if (data.get(i).compareTo(data.get(j)) < 0){
                data.swap(i , j);
                //更新i的值 , 为下一次循环作准备
                i = j;
            }else{
                break;
            }
        }
    }


    //取出堆中最大的元素 , 并把元素e加入堆中
    //方法1 : 调用extractMax()方法取出最大元素 , 然后调用add()方法加入堆中
    //这种方法进行了两次logn的操作

    //方法2 : 找到最大元素 , 也就是索引为0的元素 , 进行替换 , 然后在进行下浮操作
    public E replace(E e){
        //1.找到最大的元素
        E ret = findMax();
        //2.把最大元素替换为e
        data.set(0 , e);
        //3.进行下沉操作
        siftDown(0);
        //4.返回最大元素
        return ret;
    }






}
