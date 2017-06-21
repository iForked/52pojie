package cn.a52pojie.discuz.bean;

/**
 * Created by qtfreet00 on 2017/6/21.
 */

public class LoginBean {

    /**
     * Version : 3
     * Charset : GBK
     * Variables : {"cookiepre":"htVI_2132_","auth":"98dbTjbn/zU+UG6gs6oOBQNfjm8NVFKECH6mdHe3oAsQOVm+ab5vArOqThvMD7eQ5NwmRNAt7hw6uMzFf68V9d2XPNY","saltkey":"V09m7TSD","member_uid":"320888","member_username":"qtfreet00","member_avatar":"https://avatar.52pojie.cn/data/avatar/000/32/08/88_avatar_small.jpg","groupid":"3","formhash":"083318e2","ismoderator":null,"readaccess":"100","notice":{"newpush":"0","newpm":"0","newprompt":"24","newmypost":"0"}}
     * Message : {"messageval":"login_succeed","messagestr":"欢迎您回来，独步武林 qtfreet00，现在将转入登录前页面"}
     */

    private String Version;
    private String Charset;
    private VariablesBean Variables;
    private MessageBean Message;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getCharset() {
        return Charset;
    }

    public void setCharset(String Charset) {
        this.Charset = Charset;
    }

    public VariablesBean getVariables() {
        return Variables;
    }

    public void setVariables(VariablesBean Variables) {
        this.Variables = Variables;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean Message) {
        this.Message = Message;
    }

    public static class VariablesBean {
        /**
         * cookiepre : htVI_2132_
         * auth : 98dbTjbn/zU+UG6gs6oOBQNfjm8NVFKECH6mdHe3oAsQOVm+ab5vArOqThvMD7eQ5NwmRNAt7hw6uMzFf68V9d2XPNY
         * saltkey : V09m7TSD
         * member_uid : 320888
         * member_username : qtfreet00
         * member_avatar : https://avatar.52pojie.cn/data/avatar/000/32/08/88_avatar_small.jpg
         * groupid : 3
         * formhash : 083318e2
         * ismoderator : null
         * readaccess : 100
         * notice : {"newpush":"0","newpm":"0","newprompt":"24","newmypost":"0"}
         */

        private String cookiepre;
        private String auth;
        private String saltkey;
        private String member_uid;
        private String member_username;
        private String member_avatar;
        private String groupid;
        private String formhash;
        private Object ismoderator;
        private String readaccess;
        private NoticeBean notice;

        public String getCookiepre() {
            return cookiepre;
        }

        public void setCookiepre(String cookiepre) {
            this.cookiepre = cookiepre;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getSaltkey() {
            return saltkey;
        }

        public void setSaltkey(String saltkey) {
            this.saltkey = saltkey;
        }

        public String getMember_uid() {
            return member_uid;
        }

        public void setMember_uid(String member_uid) {
            this.member_uid = member_uid;
        }

        public String getMember_username() {
            return member_username;
        }

        public void setMember_username(String member_username) {
            this.member_username = member_username;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getFormhash() {
            return formhash;
        }

        public void setFormhash(String formhash) {
            this.formhash = formhash;
        }

        public Object getIsmoderator() {
            return ismoderator;
        }

        public void setIsmoderator(Object ismoderator) {
            this.ismoderator = ismoderator;
        }

        public String getReadaccess() {
            return readaccess;
        }

        public void setReadaccess(String readaccess) {
            this.readaccess = readaccess;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public static class NoticeBean {
            /**
             * newpush : 0
             * newpm : 0
             * newprompt : 24
             * newmypost : 0
             */

            private String newpush;
            private String newpm;
            private String newprompt;
            private String newmypost;

            public String getNewpush() {
                return newpush;
            }

            public void setNewpush(String newpush) {
                this.newpush = newpush;
            }

            public String getNewpm() {
                return newpm;
            }

            public void setNewpm(String newpm) {
                this.newpm = newpm;
            }

            public String getNewprompt() {
                return newprompt;
            }

            public void setNewprompt(String newprompt) {
                this.newprompt = newprompt;
            }

            public String getNewmypost() {
                return newmypost;
            }

            public void setNewmypost(String newmypost) {
                this.newmypost = newmypost;
            }
        }
    }

    public static class MessageBean {
        /**
         * messageval : login_succeed
         * messagestr : 欢迎您回来，独步武林 qtfreet00，现在将转入登录前页面
         */

        private String messageval;
        private String messagestr;

        public String getMessageval() {
            return messageval;
        }

        public void setMessageval(String messageval) {
            this.messageval = messageval;
        }

        public String getMessagestr() {
            return messagestr;
        }

        public void setMessagestr(String messagestr) {
            this.messagestr = messagestr;
        }
    }
}
