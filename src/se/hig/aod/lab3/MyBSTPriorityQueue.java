package se.hig.aod.lab3;

/**
 * Implementation of the {@link MyPriorityQueue} using a binary AVL tree
 *
 * @param <T>
 *            the type to store
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
@SuppressWarnings("hiding")
public class MyBSTPriorityQueue<T extends Comparable<? super T>> implements MyPriorityQueue<T>
{
    MyBSTPriorityQueueNodeHolder root = new MyBSTPriorityQueueNodeHolder(null);
    int size = 0;

    /**
     * {@inheritDoc}
     **/
    @Override
    public void clear()
    {
        root.set(null);
        size = 0;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public boolean isEmpty()
    {
        return root.isEmpty();
    }

    /**
     * {@inheritDoc} <br>
     * <br>
     * There is no limit, so false is always returned
     * 
     * @return true
     **/
    @Override
    public boolean isFull()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public int size()
    {
        return size;
    }

    int getSizeRecursive()
    {
        return root == null ? 0 : root.getSize();
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void enqueue(T element)
    {
        if (element == null)
            throw new MyPriorityQueueNullNotAllowedException("You are not allowed to insert 'null' in the queue");

        MyBSTPriorityQueueNode newNode = new MyBSTPriorityQueueNode(element);
        if (isEmpty())
            root.set(newNode);
        else
            root.add(newNode);

        size++;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public T dequeue()
    {
        if (isEmpty())
            throw new MyPriorityQueueIsEmptyException("You cannot dequeue on a empty list");

        MyBSTPriorityQueueNodeHolder target = root.node.getBiggest().holder;
        T value = target.node.data;

        if (root == target) // No more right, take root
            root.set(root.node.left.node);
        else
        {
            target.set(target.node.left.node);
            target.parent.holder.balance();
        }

        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public T getFront()
    {
        if (isEmpty())
            throw new MyPriorityQueueIsEmptyException("You cannot get front on a empty list");

        return root.node.getBiggest().data;
    }

    void print()
    {
        subPrint(root.node, 0);
    }

    void subPrint(MyBSTPriorityQueueNode node, int depth)
    {
        if (node == null)
            return;

        subPrint(node.right.node, depth + 1);
        System.out.println(new String(new char[depth]).replace('\0', '\t') + node.data);
        subPrint(node.left.node, depth + 1);
    }

    class MyBSTPriorityQueueNodeHolder
    {
        MyBSTPriorityQueueNode parent;
        MyBSTPriorityQueueNode node;

        MyBSTPriorityQueueNodeHolder(MyBSTPriorityQueueNode parent)
        {
            this.parent = parent;
        }

        boolean isEmpty()
        {
            return node == null;
        }

        void set(MyBSTPriorityQueueNode node)
        {
            this.node = node;

            if (node != null)
                node.holder = this;
        }

        MyBSTPriorityQueueNode get()
        {
            return node;
        }

        int getSize()
        {
            return isEmpty() ? 0 : node.getSize();
        }

        int getHeight()
        {
            return isEmpty() ? 0 : node.getHeight();
        }

        void add(MyBSTPriorityQueueNode node)
        {
            if (isEmpty())
            {
                set(node);
                if (parent != null)
                    parent.holder.balance();
            }
            else
            {
                this.node.add(node);
            }
        }

        MyBSTPriorityQueueNode rotate(MyBSTPriorityQueueNode child)
        {
            if (isEmpty())
                throw new MyPriorityQueueInternalException("Holder got no node");

            if (node.left.node == child)
            {
                node.left.set(child.right.node);
                child.right.set(node);
            }
            else
                if (node.right.node == child)
                {
                    node.right.set(child.left.node);
                    child.left.set(node);
                }
                else
                    throw new MyPriorityQueueInternalException("Element is not a child");

            set(child);
            return child;
        }

        void balance()
        {
            if (isEmpty())
                return;

            int leftHeight = node.left.getHeight();
            int rightHeight = node.right.getHeight();

            if (Math.abs(leftHeight - rightHeight) > 1)
            {
                if (leftHeight > rightHeight)
                {
                    if (node.left.node.left.getHeight() > node.left.node.right.getHeight())
                        rotate(node.left.node);
                    else
                        rotate(node.left.rotate(node.left.node.right.node));
                }
                else
                {
                    if (node.right.node.right.getHeight() < node.right.node.left.getHeight())
                        rotate(node.right.rotate(node.right.node.left.node));
                    else
                        rotate(node.right.node);
                }
            }
            else
            {
                if (this != root)
                    parent.holder.balance();
            }
        }
    }

    class MyBSTPriorityQueueNode
    {
        MyBSTPriorityQueueNodeHolder holder;

        MyBSTPriorityQueueNodeHolder left = new MyBSTPriorityQueueNodeHolder(this);
        T data;
        MyBSTPriorityQueueNodeHolder right = new MyBSTPriorityQueueNodeHolder(this);

        MyBSTPriorityQueueNode(T data)
        {
            this.data = data;
        }

        MyBSTPriorityQueueNode getBiggest()
        {
            return right.isEmpty() ? this : right.node.getBiggest();
        }

        MyBSTPriorityQueueNode getSmalest()
        {
            return left.isEmpty() ? this : left.node.getBiggest();
        }

        void add(MyBSTPriorityQueueNode node)
        {
            if (node.data.compareTo(data) >= 0)
                right.add(node);
            else
                left.add(node);
        }

        int getSize()
        {
            int size = 1;
            size += left.getSize();
            size += right.getSize();
            return size;
        }

        int getLeftHeight()
        {
            return left.getSize();
        }

        int getRightHeight()
        {
            return left.getSize();
        }

        int getHeight()
        {
            return 1 + Math.max(left.getHeight(), right.getHeight());
        }
    }
}
