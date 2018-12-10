using UnityEngine;
using UnityEngine.AI;
using UnityEngine.EventSystems;

public class PlayerMovement : MonoBehaviour {

    public float speed = 20f;

    private Vector3 target;
    private Animator anim;
    private NavMeshAgent navMeshAgent;
    
    int floorMask;
    float camRayLength = 300f;

    private void Awake()
    {
        floorMask = 3;
        anim = GetComponent<Animator>();
        navMeshAgent = GetComponent<NavMeshAgent>();
        target = Vector3.zero;
    }

    private void FixedUpdate()
    {
        Move();
        Animate();
        print("Current: x = " + navMeshAgent.nextPosition.x + " z = " + navMeshAgent.nextPosition.z);
        print("Target: x = " + target.x + " z = " + target.z);
    }

    private void Move()
    {
        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
        RaycastHit hit;
        if (Input.GetButtonDown("Fire2"))
        {
            if (Physics.Raycast(ray, out hit, camRayLength, floorMask))
            {
                target = hit.point - transform.position;
                target.y = 0f;
                navMeshAgent.destination = target;
                navMeshAgent.isStopped = false;
            }
        }
    }

    void Animate()
    {
        bool walking = navMeshAgent.remainingDistance > navMeshAgent.stoppingDistance
            || navMeshAgent.hasPath 
            || Mathf.Abs(navMeshAgent.velocity.sqrMagnitude) >= float.Epsilon;
 
        anim.SetBool("IsWalking", walking);
    }
}
