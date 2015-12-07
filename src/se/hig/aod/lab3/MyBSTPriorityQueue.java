package se.hig.aod.lab3;












//OBS!!!!!!!!!!!!!!!!!!!!!


// REDO WITH AVL TREE








/**
 * Implementation of the {@link MyPriorityQueue} using a binary tree
 *
 * @param <T>
 *            the type to store
 * @author Viktor Hanstorp (ndi14vhp@student.hig.se)
 */
@SuppressWarnings("hiding")
public class MyBSTPriorityQueue<T extends Comparable<? super T>> implements MyPriorityQueue<T>
{
    MyBSTPriorityQueueNode root;
    int size = 0;

    /**
     * {@inheritDoc}
     **/
    @Override
    public void clear()
    {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public boolean isEmpty()
    {
        return root == null;
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
            throw new MyPriorityQueueNullNotAllowedException();

        MyBSTPriorityQueueNode newNode = new MyBSTPriorityQueueNode(element);
        if (isEmpty())
            root = newNode;
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
            throw new MyPriorityQueueIsEmptyException();

        MyBSTPriorityQueueNode target = root.getBiggest();

        if (root == target) // No more right, take root
            root = root.left.setParent(null);
        else
            // get right
            if (target.left != null)
                target.parent.setRight(target.left);

        size++;
        return target.data;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public T getFront()
    {
        if (isEmpty())
            throw new MyPriorityQueueIsEmptyException();

        return root.data;
    }

    class MyBSTPriorityQueueNode
    {
        MyBSTPriorityQueueNode parent;

        MyBSTPriorityQueueNode left;
        T data;
        MyBSTPriorityQueueNode right;

        MyBSTPriorityQueueNode(T data)
        {
            this.data = data;
        }

        MyBSTPriorityQueueNode getBiggest()
        {
            if (right != null)
                return right.getBiggest();
            else
                return this;
        }

        MyBSTPriorityQueueNode getSmalest()
        {
            if (left != null)
                return left.getSmalest();
            else
                return this;
        }

        MyBSTPriorityQueueNode setParent(MyBSTPriorityQueueNode parent)
        {
            this.parent = parent;
            return this;
        }

        void add(MyBSTPriorityQueueNode node)
        {
            if (node.data.compareTo(data) >= 0)
                if (right != null)
                    right.add(node);
                else
                    right = node.setParent(this);
            else
                if (left != null)
                    left.add(node);
                else
                    left = node.setParent(this);
        }

        MyBSTPriorityQueueNode setRight(MyBSTPriorityQueueNode node)
        {
            right = node;
            node.parent = this;
            return this;
        }

        MyBSTPriorityQueueNode setLeft(MyBSTPriorityQueueNode node)
        {
            left = node;
            node.parent = this;
            return this;
        }

        int getSize()
        {
            int size = 1;
            if (left != null)
                size += left.getSize();
            if (right != null)
                size += right.getSize();
            return size;
        }
    }
}
