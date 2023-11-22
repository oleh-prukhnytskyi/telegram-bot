<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/includes/header.jsp" />

<div class="container mt-4">
<div class="row flex-wrap">
  <div class="col-12 col-sm-3 p-0 overflow-auto chats border">
    <div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-body-tertiary">
      <div class="list-group list-group-flush border-bottom scrollarea">
        <c:forEach var="chatSidebar" items="${chats}">
          <a href="/admin/chat/${chatSidebar.chatId}"
            class="list-group-item list-group-item-action py-3 lh-sm ${chat.chatId == chatSidebar.chatId ? 'active' : ''}">
            <div class="d-flex w-100 align-items-center justify-content-between">
              <strong class="mb-1">${chatSidebar.username}</strong>
            </div>
          </a>
        </c:forEach>
      </div>
    </div>
  </div>
  <div class="col-12 col-sm-9 chat d-flex flex-column border">
    <c:if test="${not empty chat}">
      <p class="d-flex align-items-center flex-shrink-0 p-3 text-decoration-none mb-0">
        <span class="fs-5 fw-semibold">${chat.username}</span>
      </p>

      <div class="h-100 row row-cols-1 flex-column overflow-auto flex-nowrap pt-3 pb-3 bg-light border-top border-bottom" id="chat">
        <c:forEach var="message" items="${chat.messages}">
            <div class="col-6 mt-1 ${message.role.roleName == 'BOT' ? 'align-self-end' : ''}">
              <div class="card">
                <div class="card-body">
                  <p class="mb-0">${message.message}</p>
                  <small class="text-muted d-block text-end">
                    <fmt:parseDate value="${message.timestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="date" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${date}" />
                  </small>
                </div>
              </div>
            </div>
        </c:forEach>
      </div>

      <form method="post" action="/admin/chat/${chat.chatId}" class="row align-items-center p-3 w-100 align-self-center mb-0">
        <div class="col ps-0">
          <input type="text" class="form-control" required name="message" placeholder="Write a message...">
        </div>
        <div class="col-auto pe-0">
          <button type="submit" class="btn btn-primary me-0">Send</button>
        </div>
      </form>
    </c:if>
  </div>
</div>
</div>

<script>
    const chat = document.getElementById("chat");
    chat.scrollTo(0, chat.scrollHeight);
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />