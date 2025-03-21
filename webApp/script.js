async function getStream() {
    try {
        const response = await fetch('http://localhost:8080/api/v1/stream', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error('Error fetching stream');
        }
        const stream = await response.json();
        renderStream(stream);
    } catch (error) {
        console.error('Error fetching data:', error);
        document.querySelector('.postStream').innerHTML = `
        <div class="error-message">
            <h2>Oops! Something went wrong</h2>
            <p>We couldn't load the feed at this moment. This might be due to a server issue or an internet connection problem.</p>
            <p><strong>Please try again later.</strong></p>
            <p>If the problem persists, contact technical support.</p>
        </div>
    `;
    }
}

const renderStream = (stream) => {
    const container = document.querySelector('.postStream');
    if (container.style.display === 'none') {
        showPostStream();
    }
    
    container.innerHTML = '';
    const posts = stream.posts || [];  
    if (Array.isArray(posts)) {
        if (posts.length === 0) {
            container.innerHTML = '<p>No posts yet.</p>';
            return;
        }
        posts.forEach(post => {  
            const streamElement = document.createElement('div');
            streamElement.classList.add('stream', 'post');
            streamElement.innerHTML = `
                <div class="post" id=${post.id}>
                    <div class="post-header">
                        <div class="post-user-icon">
                            <img src="usuario.png" />
                        </div>
                        <strong>${post.namename}</strong>
                    </div>
                    <div class="post-message">
                        ${post.message}
                    </div>
                    <div class="post-footer">
                        <button class="like-button" id=${post.id} onclick="toggleLike(this)">
                            <img src="copas.png" />
                        </button>
                        <span class="like-count">${post.likes}</span>
                    </div>
                </div>
            `;
            streamElement.addEventListener('click', () => {
                viewPostDetails(`${post.id}`);
            });
            container.appendChild(streamElement);
        });
    } else {
        console.error('Expected an array of posts but received:', typeof posts);
        container.innerHTML = '<p>Could not load posts. Please try again later.</p>';
    }
};

async function toggleLike(button) {
    const postId = button.id;
    try {
        const response = await fetch(`http://localhost:8080/api/v1/posts/like/${postId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        }); 
        const post = await response.json();
        if (!response.ok) {
            throw new Error("Error liking the post");
        }
        const container = document.querySelector('.postStream');
        if (container.style.display === 'none') {
            renderPostDetail(post);
        } else {
            getStream();
        }
    } catch (error) {
        console.error("Error liking the post:", error);
    }
}

async function sendPost() {
    const content = document.getElementById("postContent").value;
    const postData = {
        message: content
    };
    try {
        const response = await fetch("http://localhost:8080/api/v1/stream", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(postData)
        });
        const stream = await response.json();
        if (response.ok) {
            renderStream(stream);
        }
    } catch (error) {
        console.error('Error sending post:', error);
        document.querySelector('.postStream').innerHTML = `
        <div class="error-message">
            <h2>Oops! Something went wrong</h2>
            <p>We couldn't load the feed at this moment. This might be due to a server issue or an internet connection problem.</p>
            <p><strong>Please try again later.</strong></p>
            <p>If the problem persists, contact technical support.</p>
        </div>
    `;
    }
}

async function viewPostDetails(postId) {
    try {
        const response = await fetch(`http://localhost:8080/api/v1/posts/${postId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Error fetching post details");
        }

        const post = await response.json();
        renderPostDetail(post);
    } catch (error) {
        console.error("Error fetching post details:", error);
    }
}

const renderPostDetail = (post) => {
    const container = document.querySelector('.postDetailContainer');
    const postStream = document.querySelector('.postStream');
    postStream.style.display = 'none';
    container.style.display = 'block'; 
    container.innerHTML = `
        <div class="post-detail">
            <div class="post-header">
                <div class="post-user-icon">
                    <img src="usuario.png" />
                </div>
                <strong>${post.namename}</strong>
            </div>
            <div class="post-message">${post.message}</div>
            <div class="post-footer">
                <button class="like-button" id="${post.id}" onclick="toggleLike(this)">
                    <img src="copas.png" />
                </button>
                <span class="like-count">${post.likes}</span>
            </div>
            <button class="back-button" onclick="showPostStream()">⬅ Back</button>
            <button class="delete-button" onclick="deletePost(${post.id})">Delete</button>
        </div>
    `;
};

async function deletePost(idPost) {
    try {
        const response = await fetch(`http://localhost:8080/api/v1/stream/${idPost}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Error fetching post delete");
        }
        getStream();

    } catch (error) {
        console.error("Error fetching post details:", error);
    }
    
}

function showPostStream() {
    document.querySelector('.postStream').style.display = 'flex'; 
    document.querySelector('.postDetailContainer').style.display = 'none'; 
    getStream();
}

document.addEventListener("DOMContentLoaded", () => {
    getStream();  
});
