<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
<#--    Welcome on Codeforces!-->
    <#if post?has_content>
        <@c.post post=post/>
        <div class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value=""/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text"></textarea>
                        </div>
                        <@c.error "post.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
        <#list post.comments as cs>
            <div>${cs.user.login}</div>
            <div>${cs.text}</div>
        </#list>
    <#else>
        <div>No such User</div>
    </#if>
</@c.page>
