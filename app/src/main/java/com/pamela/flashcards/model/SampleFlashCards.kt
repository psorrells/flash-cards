package com.pamela.flashcards.model

val kubernetesDeck = FlashCardDeckDomain(name = "Kubernetes")
val kubernetesFlashCards = listOf(
    FlashCardDomain(
        front = "What is Kubernetes?",
        back = "Kubernetes is an open-source container orchestration platform for automating the deployment, scaling, and management of containerized applications."
    ),
    FlashCardDomain(
        front = "Explain the concept of a Pod in Kubernetes.",
        back = "A Pod is the smallest deployable unit in Kubernetes, representing a single instance of a running process. It can contain one or more containers."
    ),
    FlashCardDomain(
        front = "What is a Kubernetes Cluster?",
        back = "A Kubernetes Cluster is a set of nodes that run containerized applications. It consists of a control plane and a set of worker nodes."
    ),
    FlashCardDomain(
        front = "Describe the role of the Kubernetes Master in a cluster.",
        back = "The Kubernetes Master is responsible for managing the overall state of the cluster. It includes components like API server, controller manager, and scheduler."
    ),
    FlashCardDomain(
        front = "What is a Service in Kubernetes?",
        back = "A Service is an abstraction that defines a logical set of Pods and a policy by which to access them. It enables communication between different parts of an application."
    ),
    FlashCardDomain(
        front = "Explain the purpose of a Kubernetes Deployment.",
        back = "A Deployment in Kubernetes allows you to describe and declaratively manage a set of replica Pods. It ensures that a specified number of replicas are running at all times."
    ),
    FlashCardDomain(
        front = "What is a Kubernetes Namespace?",
        back = "A Namespace provides a way to divide cluster resources between multiple users or teams. It allows you to create isolated environments within the same cluster."
    ),
    FlashCardDomain(
        front = "Describe the difference between a StatefulSet and a Deployment in Kubernetes.",
        back = "While a Deployment is suitable for stateless applications, a StatefulSet is designed for stateful applications, providing stable network identities and persistent storage."
    ),
    FlashCardDomain(
        front = "What is a Container Image in the context of Kubernetes?",
        back = "A Container Image is a lightweight, standalone, and executable software package that includes everything needed to run a piece of software, including the code, runtime, libraries, and system tools."
    ),
    FlashCardDomain(
        front = "Explain the concept of Ingress in Kubernetes.",
        back = "Ingress is an API object that manages external access to services within a cluster, typically handling external HTTP and HTTPS traffic. It allows you to define routing rules for external traffic."
    )
)

val awsDeck = FlashCardDeckDomain(name = "AWS")
val awsFlashCards = listOf(
    FlashCardDomain(
        front = "What is AWS?",
        back = "AWS (Amazon Web Services) is a cloud computing platform provided by Amazon, offering a wide range of services including computing power, storage, databases, machine learning, analytics, and more."
    ),
    FlashCardDomain(
        front = "Explain the concept of an EC2 instance in AWS.",
        back = "An EC2 (Elastic Compute Cloud) instance is a virtual server in the AWS cloud, allowing users to run applications. Users can choose the instance type, configure security, and scale as needed."
    ),
    FlashCardDomain(
        front = "What is S3 in AWS?",
        back = "S3 (Simple Storage Service) is a scalable object storage service provided by AWS. It allows users to store and retrieve any amount of data at any time, and is commonly used for backup, archiving, and data storage."
    ),
    FlashCardDomain(
        front = "Describe the purpose of AWS Lambda.",
        back = "AWS Lambda is a serverless computing service that lets you run code without provisioning or managing servers. It automatically scales and executes code in response to trigger events."
    ),
    FlashCardDomain(
        front = "What is AWS IAM?",
        back = "AWS IAM (Identity and Access Management) is a service that enables you to securely control access to AWS services and resources. It allows you to create and manage users, groups, and permissions."
    ),
    FlashCardDomain(
        front = "Explain the role of Amazon RDS in AWS.",
        back = "Amazon RDS (Relational Database Service) is a managed relational database service that makes it easier to set up, operate, and scale a relational database in the AWS Cloud."
    ),
    FlashCardDomain(
        front = "What is AWS Elastic Beanstalk?",
        back = "AWS Elastic Beanstalk is a fully managed service that makes it easy to deploy and run applications in multiple languages. It automatically handles capacity provisioning, load balancing, and application health monitoring."
    ),
    FlashCardDomain(
        front = "Describe the purpose of AWS CloudFormation.",
        back = "AWS CloudFormation is a service that enables you to define and provision AWS infrastructure as code. It allows you to create, update, and delete resources in a safe and predictable manner."
    ),
    FlashCardDomain(
        front = "What is AWS VPC?",
        back = "AWS VPC (Virtual Private Cloud) is a logically isolated section of the AWS Cloud where you can launch AWS resources in a defined virtual network. It allows you to have control over the virtual networking environment."
    ),
    FlashCardDomain(
        front = "Explain the concept of AWS Elastic Load Balancing.",
        back = "AWS Elastic Load Balancing automatically distributes incoming application traffic across multiple targets, such as EC2 instances, containers, and IP addresses, in one or more availability zones."
    ),
)

val dataDeck = FlashCardDeckDomain(name = "Data Structures and Algorithms")
val dataFlashCards = listOf(
    FlashCardDomain(
        front = "What is a Data Structure?",
        back = "A Data Structure is a way of organizing and storing data to perform operations efficiently. It defines the relationship between the data and the operations that can be performed on the data."
    ),
    FlashCardDomain(
        front = "Explain the concept of an Array.",
        back = "An Array is a linear data structure that stores elements of the same type in contiguous memory locations. Elements can be accessed by their index, and arrays are useful for random access."
    ),
    FlashCardDomain(
        front = "What is a Linked List?",
        back = "A Linked List is a linear data structure where elements are stored in nodes, and each node points to the next node in the sequence. It allows dynamic memory allocation and efficient insertion and deletion operations."
    ),
    FlashCardDomain(
        front = "Describe the purpose of a Stack.",
        back = "A Stack is a data structure that follows the Last In, First Out (LIFO) principle. It allows elements to be added and removed only from one end, known as the top."
    ),
    FlashCardDomain(
        front = "Explain the concept of a Queue.",
        back = "A Queue is a data structure that follows the First In, First Out (FIFO) principle. It allows elements to be added at one end (enqueue) and removed from the other end (dequeue)."
    ),
    FlashCardDomain(
        front = "What is a Binary Tree?",
        back = "A Binary Tree is a hierarchical data structure consisting of nodes, where each node has at most two children, referred to as the left child and the right child."
    ),
    FlashCardDomain(
        front = "Describe the purpose of Hashing in Data Structures.",
        back = "Hashing is a technique used to map data of arbitrary size to fixed-size values, usually for fast data retrieval. It is commonly used in implementing hash tables."
    ),
    FlashCardDomain(
        front = "What is an Algorithm?",
        back = "An Algorithm is a step-by-step procedure or a set of rules for solving a specific problem. It defines a sequence of operations to be performed to accomplish a particular task."
    ),
    FlashCardDomain(
        front = "Explain the concept of Big O notation.",
        back = "Big O notation is a way of expressing the upper bound of the time complexity of an algorithm in terms of the input size. It provides an asymptotic analysis of an algorithm's efficiency."
    ),
    FlashCardDomain(
        front = "Describe the purpose of Depth-First Search (DFS) in Graphs.",
        back = "Depth-First Search is an algorithm used to traverse or search tree or graph data structures. It starts at the root and explores as far as possible along each branch before backtracking."
    ),
)

