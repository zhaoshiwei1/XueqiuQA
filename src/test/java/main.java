public class main
{
    public static void main(String[] args)
    {
        String path = "A/B/c/10080/[0]";
        String[] pathList = path.split("/");
        for(String i : pathList)
        {
            System.out.println(i);
        }
    }
}
